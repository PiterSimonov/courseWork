$(document).ready(function () {
    $('.date').prop("min", function () {
        return new Date().toJSON().split('T')[0];
    });


    $("#fromDate").change(function() {
            var x = fromDate.value;
            var d = new Date(Date.parse(x));
            var nextDay = dateToString(d, 1);
            var maxDays = dateToString(d, 28);
            var $toDate = $("#toDate");
            $toDate.prop("max", maxDays);
            $toDate.prop("value", nextDay);
            $toDate.prop("min", nextDay);
    });

    function dateToString(d, num){
        if(num === undefined){
            return d.toJSON().split('T')[0];
        }else{
            var nextDay = new Date(d.valueOf()+24*60*60*1000*num);
            return nextDay.toJSON().split('T')[0];
        }
    }

    $("#city").keyup(function() {
        if (city.value.length >= 3){

            var $city = $("#list");
            $.ajax({
                    url: "/city",
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            var li = li = document.createElement('li');
                            li.appendChild(document.createTextNode(data[i].name));
                            li.onclick = function(){
                                alert("click");
                                //city.value = data[i].name;
                                //$("#cityId").prop("value", data[i].id);
                                $city.css("display", "none");
                            };
                            $city.append(li);
                            $city.css("display", "block");
                        }
                    }
                });
        }

        //$.ajax({
        //    url: "/city",
        //    type: "GET",
        //    dataType: "json",
        //    success: function (data) {
        //        alert("catch data"),
        //        alert(data)
        //        for (var i = 0; i < data.length; i++) {
        //            alert(data[i].name)
        //        }
        //    },
        //
        //});
    });

    $('form#choice-room').submit(function (e) {
        var userRole;
        $.ajax({
            url: "/get-user",
            type: "GET",
            async: false,
            success: function (responce) {
                userRole = responce;
            }
        });
        if (userRole == "HotelOwner" || userRole == 'CLIENT') {
            $(":submit").attr("disabled", true);
        } else {
            loadPopup();
            return false;
        }
    });

    var on = 0;

    function loadPopup() {
        if (on == 0) {
            $("#back").css("opacity", "0.6");
            $("#popup").slideDown(500);
            $("#back").fadeIn(1500);
            on = 1;
        }
    }

    function off() {
        if (on == 1) {
            $("#popup").slideUp("fast");
            $("#back").fadeOut("fast");
            on = 0;
        }
    }

    $("a#showpopup").click(function () {
        loadPopup();
    });
    $("div#back").click(function () {
        off();
    });
    $("div.close").click(function () {
        off();
    });
    $("#loginForm").submit(function (e) {
        e.preventDefault();
        var loginInput = $('input#login').val();
        if (loginInput.length < 3) {
            $("#error-box").text("Login length must be > 3");
        } else if ($('input#password').val().length < 6) {
            $("#error-box").text("Password length must be > 6");
        }
        else {
            $.ajax({
                url: '/check-user',
                type: 'POST',
                data: $("#loginForm").serialize(),
                success: function (data) {
                    if (data == 'logged') {
                        off();
                        $('.user-bar').html('<a class="menuLink" href="/profile">Profile</a>' +
                            '<a class="menuLink" href="/logout">Logout</a>')
                    } else {
                        $("#error-box").text(data);
                    }
                }
            });
        }
    });

    $('#country_id').one('click', function () {
        $.get('/search/country', function (result) {
            var option = '';
            $.each(result, function (key, data) {
                option += '<option value="' + key + '">' + data + '</option>'
            });
            $('#country_id').html(option);
        })
    }).on("change", function () {
        $('#city_id').removeAttr("disabled");
        var data = $('#country_id').val();
        $.get('/search/city', {country_id: data}, function (result) {
            var option = '';
            $.each(result, function (key, data) {
                option += '<option value="' + key + '">' + data + '</option>'
            });
            $('#city_id').html(option);
        })
    })
});