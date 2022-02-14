$(document).ready(function (){
    let btn = $("#btn_buy");

    btn.on("click", function (){

        if (btn.hasClass("btn-secondary")) {
            btn.removeClass("btn-secondary");
            btn.addClass("btn-success");
            btn.html("Добавлено");
        }
    });


    $(".slider").children().each(function (){
        if (!$(this).hasClass("slider-active")) {
            $(this).css("display", "none");
        }
    });

    let lastIndex = 0;

    $(".btn-outline-secondary").on("click", function (){

        let buttonIndex = $(this).index();
        let slideByIndex = $(".slider").children().eq(buttonIndex);

        if (buttonIndex == lastIndex) {
            return;
        }
        lastIndex = buttonIndex;

        $("button").each(function (){
            if ($(this).hasClass("active")) {
                $(this).removeClass("active");
            }
        });

        $(".slider").children().each(function (){
            if ($(this).hasClass("slider-active")) {
                $(this).removeClass("active");
                $(this).css("display", "none");
            }
        });

        slideByIndex.addClass("slider-active");
        slideByIndex.fadeIn(600);

        $(this).addClass("active");
    });
});