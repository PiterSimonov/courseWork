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
});

function dateToString(d, num) {
    if (num === undefined) {
        return d.toJSON().split('T')[0];
    } else {
        var nextDay = new Date(d.valueOf() + 24 * 60 * 60 * 1000 * num);
        return nextDay.toJSON().split('T')[0];
    }
}