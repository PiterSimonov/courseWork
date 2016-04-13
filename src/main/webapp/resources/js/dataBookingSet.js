$(document).ready(function () {

    var $fromDate = $('#fromDate');
    $fromDate.attr("value", today);
    $fromDate.attr("min", today);

    var $bookingDate = $('.booking-date');
    $bookingDate.attr("min", today);
    $bookingDate.attr("value", today);

    $fromDate.change(function () {
        $("#fromDate").attr("value", fromDate.value);
        var x = fromDate.value;
        var $endDate = $("#endDate");
        $endDate.attr("min", x);
        $endDate.attr("value", x);
        $endDate.val(x);
    });
});

var today = dateToString(new Date());

function dateToString(d) {
    return d.toJSON().split('T')[0];
}