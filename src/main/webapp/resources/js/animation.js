$(document).ready(function () {
    $('#payment').submit(function (e) {
        e.preventDefault();
        var form = this;
        $('#loaderImage').slideDown(1).delay(3000);
        setTimeout(function () {
            form.submit();
        }, 3000)
    });
});
