$(document).ready(function () {
    $('.delete-order').on('click', function (e) {
        var $thisOrder = $(this);
        $thisOrder.attr('disabled', true);
        e.preventDefault();
        var $divParent = $thisOrder.parent("div");
        var orderId = $thisOrder.attr('href');
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