$(document).ready(function () {

    $('#add-hotel').submit(function (e) {
        var $thisForm = $(this);
        var $submit = $thisForm.find(":submit");
        if ($thisForm.valid()) {
            $submit.attr("disabled", true);
            $('#wait').text("Please wait");
        }
        return true;
    });

    $('#country_id').on("change", function () {
        $('#city_id').removeAttr("disabled");
        var data = $('#country_id').val();
        $.get('/search/city', {country_id: data}, function (result) {
            var option = '';
            $.each(result, function (key, data) {
                option += '<option value="' + key + '">' + data + '</option>'
            });
            $('#city_id').html(option);
        })
    });

});
