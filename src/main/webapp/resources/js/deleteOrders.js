$(document).ready(function () {
    $('.delete-order').on('click', function (e) {
        e.preventDefault();
        var $thisOrder = $(this);
        var $divParent = $thisOrder.parent("div");
        var orderId = $(this).attr('href');
        $.ajax({
            url: "/order/" + orderId + "/delete",
            type: 'POST',
            success: function (date) {
                if (date) {
                    $divParent.remove();
                } else {
                    document.location.href = "/error?message=Your order has been deleted"
                }
            }
        });
    });
});