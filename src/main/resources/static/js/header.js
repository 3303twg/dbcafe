$(document).ready(function(){
    $(".head_wrap .head .head_menu > ul > li").hover(function() {
        if ( $(".head_menu_wrap").is(".right0") == false ) {
            $(".head_wrap .head .head_menu > ul > li").removeClass("check");
            $(this).addClass("check");
            $(".head_menu_down_menu").stop().slideDown("fast");
        };
    }, function() {
            $(".head_wrap .head .head_menu > ul > li").removeClass("check");
    });
    
    $(".head_wrap").hover(function() {
        $(this).addClass("head_over");
    }, function() {
        if ( $(".head_menu_wrap").is(".right0") == false ) {
            $(this).removeClass("head_over");
            $(".head_wrap .head .head_menu > ul > li").removeClass("check");
            $(".head_menu_down_menu").stop().slideUp("fast");
        }
    });
    });