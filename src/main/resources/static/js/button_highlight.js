$(document).ready(function (){
    let id = $("#header_btn").val();

    $("li").each(function (){
        if ($(this).hasClass("active")) {
            $(this).removeClass("active");
        }
    });

    if (id != null) {
        $("#" + id).addClass("active");
    }
});