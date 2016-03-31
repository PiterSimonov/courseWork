$(document).ready(function () {

    var d = new Date();
    var today = dateToString(d);
    var $fromDate = $('#fromDate');
    $fromDate.attr("value", today);
    $fromDate.attr("min", today);

    var $bookingDate = $('.booking-date');
    $bookingDate.attr("min", today);
    $bookingDate.attr("value", today);

    $fromDate.change(function () {
        $("#fromDate").attr("value", fromDate.value);
        var x = fromDate.value;
        var d = new Date(Date.parse(x));
        var $endDate = $("#endDate");
        $endDate.attr("min", x);
        $endDate.attr("value", x);
        $endDate.val(x);
    });

    function dateToString(d, num) {
        if (num === undefined) {
            return d.toJSON().split('T')[0];
        } else {
            var nextDay = new Date(d.valueOf() + 24 * 60 * 60 * 1000 * num);
            return nextDay.toJSON().split('T')[0];
        }
    }
});