$(document).ready(function () {
    if (localStorage.getItem('form')) {
        $('#left-panel').html(localStorage.getItem('form'));
    }
    if(localStorage.getItem("stars")){
        $('#stars').val(localStorage.getItem("stars"));
    }
});