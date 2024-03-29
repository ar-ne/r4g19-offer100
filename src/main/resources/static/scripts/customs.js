let alertCount = 0, bNotification, aNotification, aCount, userType = null, stompClient = null;
let local_notification_storage = [], bn_empty = true, a_count = null, a_removed = null;

function setUT(u) {
    userType = u;
}

function apiSubmit(url, method, dat, callback) {
    $.ajax({
        type: method,
        url: url,
        data: dat,
        dataType: "json",
        contentType: "application/json",
        headers: {'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")},
        async: true,
        cache: false,
        complete: function (data) {
            callback(data);
        }
    });
}

function formSubmit(button = null, form = 'form', callBefore = null, callAfter = null) {
    form = $('#' + form);
    if (button !== null) $('#' + button).attr("disabled", "");
    if (callBefore !== null) callBefore();
    doSubmit(form);
    if (callAfter !== null) callAfter();
    if (button !== null) $('#' + button).removeAttr("disabled");
    return false;
}

function doSubmit(form) {
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        data: form.serialize(),
        dataType: "xhr",
        async: true,
        cache: false,
        complete: function (xhr) {
            inPageAction(xhr.status, xhr.responseText);
        }
    });
}

function inPageAction(code, args) {
    switch (code) {
        case 200:
            Turbolinks.visit(args);
            break;
        case 201:
            Turbolinks.visit(args, {action: "replace"});
            break;
        case 203:
            eval(args.toString());
            break;
        case 202:
            showSuccessAlert(args.toString());
            break;
        case 205:
            showSuccessAlert();
            break;
        case 406:
            showFailAlert(args.toString());
            break;
        default:
            showFailAlert();
            break;
    }
}

function showSuccessAlert(msg = null, time = 3000, callback = null) {
    showAlert(msg === null ? "成功/完成" : msg, time, callback);

}

function showFailAlert(msg = null, time = 3000, callback = null) {
    showAlert(msg === null ? "错误/失败" : msg, time, callback, "danger", "clear");
}

function showAlert(message, time = -1, callback = null, type = 'success', icon = 'check') {
    type = "alert-" + type;
    const current = alertCount++;
    $("#alert").append("<div class='alert " + type + " alert-dismissible fade mb-0' role='alert'>\n" +
        "                <button id='alertCount-" + current + "' type=\"button\" class='close' data-dismiss='alert' aria-label='Close'>\n" +
        "                    <span aria-hidden='true'>×</span>\n" +
        "                </button>\n" +
        "                <i class='material-icons'>" + icon + "</i>\n" + message +
        "</div>");
    ScrollTop(0, 100);
    setTimeout(function () {
        $("#alert > div").addClass("show")
    }, 10);
    if (time !== -1)
        setTimeout(function () {
            dismiss(current);
            if (callback !== null) callback();
        }, time)
}

function dismiss(id) {
    $("#alertCount-" + id).click();
}

function dismissAll() {
    $("#alert > div > button").click();
}

function base64img_encoder(localFile) {
    let r = new FileReader();
    let base64img;
    r.onload = function () {
        base64img = r.result;
    };
    return base64img;
}

function base64img_render(base64img, container) {
    container.src = '';
    container.src = base64img;
}

//TODO:将第一次请求内容改为只有一个空元素
function generateTables(dataURL, table, checkbox = true, container = 'table', operate = false) {
    $.get(dataURL, function (jsonTable, status) {
        if (status !== "success") return;
        if (jsonTable.length < 1) {
            $('#' + container).text("暂无数据")
        }
        const col = [];
        for (let key in jsonTable[0]) {
            if (col.indexOf(key) === -1) {
                col.push({
                    field: key.toString(),
                    sortable: true,
                });
            }
        }
        if (col.length === 0) return;
        $.ajax({
            type: "POST",
            url: "/api/public/lang/bs-table/" + table,
            dataType: 'json',
            headers: {'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")},
            async: false,
            contentType: "application/json",
            data: JSON.stringify(col),
            success: function (data) {
                if (checkbox) data.splice(0, 0, {checkbox: true});
                if (operate) data.push({
                    field: '操  作',
                    title: '操  作',
                    align: 'center',
                    clickToSelect: false,
                    events: operate.events,
                    formatter: operate.formatter
                });
                $('#' + container).bootstrapTable({
                    locale: 'zh-CN',
                    pagination: true,
                    search: true,
                    silentSort: false,
                    maintainSelected: true,
                    clickToSelect: true,
                    checkboxHeader: checkbox,
                    columns: data,
                    data: jsonTable,
                    url: dataURL,
                    showRefresh: true,
                    showPaginationSwitch: true,
                    showToggle: true,
                    smartDisplay: true,
                    theadClasses: 'bg-light',
                    classes: 'table mb-0',

                });

                //modify table checkbox
                // let cbList = $("#" + container + " > tbody > tr >td.bs-checkbox");
                // for (let i = 0; i < cbList.length; i++) {
                //     cbList[i].innerHTML = "";
                //     cbList[i].innerHTML = ("<div class='custom-control custom-checkbox'>" +
                //         "<input type='checkbox' class='custom-control-input' name='btSelectItem' data-index='" + i + "'>" +
                //         "<label class=\"custom-control-label\"></label></div>")
                // }
            }
        });
    });
}


Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};
Date.prototype.defFmt = function (fmt) {
    return this.format("yyyy-MM-dd hh:mm:ss");
};

function goBack() {
    history.back(-1);
}

function init() {
    initPopper();
    if (stompClient === null || !stompClient.connected) connect(userType);
    update_notification_dom();
    sync_local();
}

function initPopper() {
    const t = {duration: 270, easing: "easeOutSine"};
    const p = $(":not(.main-sidebar--icons-only) .dropdown");
    p.on("show.bs.dropdown", function () {
        $(this).find(".dropdown-menu").first().stop(!0, !0).slideDown(t)
    });
    p.on("hide.bs.dropdown", function () {
        $(this).find(".dropdown-menu").first().stop(!0, !0).slideUp(t)
    });
    $(".toggle-sidebar").on('click', function (t) {
        $(".main-sidebar").toggleClass("open")
    })
}

const ScrollTop = (number = 0, time) => {
    if (!time) {
        document.body.scrollTop = document.documentElement.scrollTop = number;
        return number;
    }
    const spacingTime = 20; // 设置循环的间隔时间  值越小消耗性能越高
    let spacing = time / spacingTime; // 计算循环的次数
    let nowTop = document.body.scrollTop + document.documentElement.scrollTop; // 获取当前滚动条位置
    let everTop = (number - nowTop) / spacing; // 计算每次滑动的距离
    let scrollTimer = setInterval(() => {
        if (spacing > 0) {
            spacing--;
            ScrollTop(nowTop += everTop);
        } else {
            clearInterval(scrollTimer); // 清除计时器
        }
    }, spacingTime);
};

function connect(channel) {
    const socket = new SockJS('/ws', {transports: 'websocket'});
    window.socket = socket;
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/bc/' + channel, function (data) {
            on_receive_notification(JSON.parse(data.body));
        });
        stompClient.subscribe('/user/topic/uc', function (data) {
            on_receive_notification(JSON.parse(data.body));
        });
        stompClient.subscribe('/user/topic/sync', function (data) {
            local_notification_storage = [];
            bn_empty = true;
            a_count = a_removed = null;
            sync_server(data);
        });
        stompClient.subscribe('/topic/live', function (data) {
            if (data.body === "NX") {
                if (window.updateChart !== undefined) window.updateChart();
            }
        });
        sync_server();
    });
}

function on_receive_notification(dat) {
    let index = local_notification_storage.length;
    local_notification_storage.push(dat);
    add_aNotification(local_notification_storage[index], index);
    add_bNotification(local_notification_storage[index], index);
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function send(dat) {
    stompClient.send("/app/read", {}, JSON.stringify(dat));
}

/**
 * if index === null, then markAllAsRead
 * @param index index of that notification(in an array)
 */
function markAsRead(index = null) {
    let nt = [];
    if (index === null) {
        for (let i = 0; i < local_notification_storage.length; i++) {
            nt.push(local_notification_storage[i].nid);
        }
        send(nt);
        sync_server();
        return;
    }
    nt.push(local_notification_storage[index].nid);
    send(nt);
    remove_notification(index);
}

function add_bNotification(dat, local_index) {
    if (bNotification) {
        let html = "<div data-url='" + dat.url + "'" + " data-index='" + local_index +
            "' class='card card-small card-post mb-4 button'>" +
            "                <div class='card-body' onclick='notification_click(this)'>" +
            "                    <h5 class='card-title'>" + dat.title + "</h5>" +
            "                    <p class='card-text text-muted'>" + dat.content + "</p>" +
            "                </div><div class='card-footer border-top d-flex'>" +
            "                    <div class='card-post__author d-flex'>" +
            "                        <div class='d-flex flex-column justify-content-center ml-3'>" +
            "                            <small class='text-muted'>" + dat.sender + "  " + dat.time + "</small>" +
            "                        </div></div>" +
            "                    <div class='my-auto ml-auto'>\n" +
            "                        <button data-index='" + dat.nid + "' class='btn btn-sm btn-white' onclick=\"markAsRead(this.dataset.index)\">" +
            "                            <i class='far fa-bookmark mr-1'></i> 标记为已读\n" +
            "                        </button></div></div></div>";
        if (bn_empty) {
            bn_empty = false;
            bNotification.empty();
        }
        bNotification.prepend(html);
    }
}

function add_aNotification(dat, local_index) {
    if (aNotification) {
        let html = "<div class='dropdown-item' " + "onclick='notification_click(this,false)'" +
            " data-url='" + dat.url + "' data-index='" + local_index + "'>" +
            "<div class='notification__content'>" +
            "<span class='notification__category'>" + dat.title + "</span>" +
            "<p>" + dat.content + "</p></div></div>";
        aNotification.prepend(html);
        set_aCount();
    }
}

function set_aCount() {
    x = local_notification_storage.length - a_removed;
    if (aCount) {
        if (x === 0) aCount.text("");
        else aCount.text(x);
        a_count = parseInt(aCount.text());
    }
}

function update_notification_dom() {
    bNotification = $("#bNotification");
    aNotification = $("#aNotification");
    aCount = $("#aCount");
    if (bNotification[0] === undefined) bNotification = false;
    if (aNotification[0] === undefined) aNotification = false;
    if (aCount[0] === undefined) aCount = false;
}

function sync_server(data = null) {
    if (data === null) {
        stompClient.send("/app/sync", {}, "");
        return;
    }
    local_notification_storage = JSON.parse(data.body).sort(function (a, b) {
        return b.time > a.time ? -1 : 1;
    });
    sync_local()
}

function sync_local(sync_a = true, sync_b = true) {
    if (a_count === local_notification_storage.length - a_removed)
        sync_a = a_count === "";
    if (!bNotification) sync_b = false;
    if (sync_a) clean_a();
    if (sync_b) clean_b();
    for (let i = 0; i < local_notification_storage.length; i++) {
        if (local_notification_storage[i] === null) continue;
        if (sync_a) add_aNotification(local_notification_storage[i], i);
        if (sync_b) add_bNotification(local_notification_storage[i], i);
    }
}

function clean_a() {
    if (!aNotification) return;
    aNotification.empty();
    set_aCount(0);
    aNotification.prepend("<a class='dropdown-item notification__all text-center' href='notification'>查看全部提醒 </a>");
}

function clean_b() {
    if (!bNotification) return;
    bNotification.empty();
    bn_empty = true;
    bNotification.prepend("<h1>暂无未读消息</h1>");
}

function notification_click(item, child = true) {
    if (child) item = item.parentNode;
    markAsRead(parseInt(item.dataset.index));
    Turbolinks.visit(item.dataset.url);
}

function remove_notification(index) {
    local_notification_storage[index] = null;
    let pending_remove = [];
    if (aNotification) {
        let childNodes = aNotification.children();
        for (let i = 0; i < childNodes.length; i++) {
            if (parseInt(childNodes[i].dataset.index) === index)
                pending_remove.push(childNodes[i]);
        }
    }
    if (aCount) {
        a_removed++;
    }
    if (bNotification) {
        let childNodes = bNotification.children();
        for (let i = 0; i < childNodes.length; i++) {
            if (parseInt(childNodes[i].dataset.index) === index)
                pending_remove.push(childNodes[i]);
        }
    }
    for (let i = 0; i < pending_remove.length; i++)
        pending_remove[i].remove();
    if (bNotification && bNotification.children().length === 0) clean_b();
    set_aCount();
}