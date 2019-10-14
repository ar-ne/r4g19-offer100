

$(".btn btn-outline-primary").send(function () {
    $(this).button('loading');//禁用按钮并显示提交中
    $(this).button('reset');//重置按钮
});