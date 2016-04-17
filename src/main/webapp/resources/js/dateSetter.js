$(document).ready(function () {

    var d = new Date();
    var today = dateToString(d);
    var nextDay = dateToString(d, 1);
    var maxDays = dateToString(d, 28);
    var $fromDate = $('#fromDate');
    var $toDate = $('#toDate');
    $fromDate.attr("value", today);
    $fromDate.attr("min", today);
    $toDate.attr("value", nextDay);
    $toDate.attr("min", nextDay);
    $toDate.attr("max", maxDays);

    var period = 1;
    $("#fromDate").change(function () {
        $("#fromDate").attr("value", fromDate.value);
        var x = fromDate.value;
        var d = new Date(Date.parse(x));
        var nextDayPeriod = dateToString(d, period);
        var nextDay = dateToString(d, 1);
        var maxDays = dateToString(d, 28);
        var $toDate = $("#toDate");
        $toDate.attr("max", maxDays);
        $toDate.attr("value", nextDayPeriod);
        $toDate.val(nextDayPeriod);
        $toDate.attr("min", nextDay);
    });
    $("#toDate").change(function () {
        var $toDate = $("#toDate");
        $toDate.attr("value", toDate.value);
        var x = toDate.value;
        var d = new Date(Date.parse(x));
        $toDate.attr("value", dateToString(d));
        $toDate.val(dateToString(d));
        var $fromDate = $("#fromDate").val();
        var day = new Date(Date.parse($fromDate));
        var periodTime = d.getTime() - day.getTime();
        period = periodTime / (1000 * 3600 * 24);
    });
});

function dateToString(d, num) {
    if (num === undefined) {
        return d.toJSON().split('T')[0];
    } else {
        var nextDay = new Date(d.valueOf() + 24 * 60 * 60 * 1000 * num);
        return nextDay.toJSON().split('T')[0];
    }
}