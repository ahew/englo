

// ACCORDION
// $(function() {
//    $( "#accordion" ).accordion({
//     heightStyle: $heighter,
//    });
// });


// RETINA

$(function () {

   if (window.devicePixelRatio == 2) {

      var images = $("img.hires");

      // loop through the images and make them hi-res
      for (var i = 0; i < images.length; i++) {

         // create new image name
         var imageType = images[i].src.substr(-4);
         var imageName = images[i].src.substr(0, images[i].src.length - 4);
         imageName += "@2x" + imageType;

         //rename image
         images[i].src = imageName;
      }
   } else if (window.devicePixelRatio > 2) {

      var images = $("img.hires");

      // loop through the images and make them hi-res
      for (var i = 0; i < images.length; i++) {

         // create new image name
         var imageType = images[i].src.substr(-4);
         var imageName = images[i].src.substr(0, images[i].src.length - 4);
         imageName += "@4x" + imageType;

         //rename image
         images[i].src = imageName;
      }
   }

});



// HEADER WINDOW HEIGHT

function setHeight() {
   windowHeight = $(window).innerHeight();
   $('section.eventa_search').css('height', windowHeight - 50);
}
;
setHeight();

$(window).resize(function () {
   setHeight();
});

// TOGGLE SAMPLE

$(document).ready(function () {
   $(".drop").click(function () {
      $(".entando_dropdown").toggleClass("down_tend");
   });
   $(".server").click(function () {
      $("#server").toggleClass("transparency");
      $("#new_job").removeClass("to_left");
   });
   $(".new_job").click(function () {
      $("#new_job").toggleClass("to_left");
      $("#server").removeClass("transparency");
   });

   $("#bubble").click(function () {
      $(".bubble").toggleClass("to_bottom");
   });

   $(".new_card").click(function () {
      $("#new_card").toggleClass("to_left");
      $("#detail_card").removeClass("to_left");
   });

   $(".lista").click(function () {
      $("#detail_card").toggleClass("to_left");
   });

});

/* MORPH BUTTON */

(function () {
   var docElem = window.document.documentElement, didScroll, scrollPosition;

   // trick to prevent scrolling when opening/closing button
   function noScrollFn() {
      window.scrollTo(scrollPosition ? scrollPosition.x : 0, scrollPosition ? scrollPosition.y : 0);
   }

   function noScroll() {
      window.removeEventListener('scroll', scrollHandler);
      window.addEventListener('scroll', noScrollFn);
   }

   function scrollFn() {
      window.addEventListener('scroll', scrollHandler);
   }

   function canScroll() {
      window.removeEventListener('scroll', noScrollFn);
      scrollFn();
   }

   function scrollHandler() {
      if (!didScroll) {
         didScroll = true;
         setTimeout(function () {
            scrollPage();
         }, 60);
      }
   }
   ;

   function scrollPage() {
      scrollPosition = {x: window.pageXOffset || docElem.scrollLeft, y: window.pageYOffset || docElem.scrollTop};
      didScroll = false;
   }
   ;

   scrollFn();

   [].slice.call(document.querySelectorAll('.morph-button')).forEach(function (bttn) {
      new UIMorphingButton(bttn, {
         closeEl: '.icon-close',
         onBeforeOpen: function () {
            // don't allow to scroll
            noScroll();
         },
         onAfterOpen: function () {
            // can scroll again
            canScroll();
         },
         onBeforeClose: function () {
            // don't allow to scroll
            noScroll();
         },
         onAfterClose: function () {
            // can scroll again
            canScroll();
         }
      });
   });


})();


// BROWSER DETECT

var is_chrome = navigator.userAgent.indexOf('Chrome') > -1;
var is_explorer = navigator.userAgent.indexOf('MSIE') > -1;
var is_firefox = navigator.userAgent.indexOf('Firefox') > -1;
var is_safari = navigator.userAgent.indexOf("Safari") > -1;
var is_Opera = navigator.userAgent.indexOf("Presto") > -1;
if ((is_chrome) && (is_safari)) {
   is_safari = false;
}

if (navigator.userAgent.indexOf('Safari') != -1 && navigator.userAgent.indexOf('Chrome') == -1) {
   $('html').addClass('saf');
}
if (navigator.userAgent.indexOf('Chrome') != -1) {
   $('html').addClass('chr');
}
if (navigator.userAgent.indexOf('Firefox') != -1) {
   $('html').addClass('ffx');
}
if (navigator.userAgent.indexOf('MSIE') != -1) {
   $('html').addClass('MIE');
}









/* NUOVO EFFETTO APPLE TV */

var start_x = null;
var start_y = null;

function prefixifyer(sel, prop, val) {
   $("" + sel + "").css("-webkit-" + prop + "", "" + val + "");
   $("" + sel + "").css("-moz-" + prop + "", "" + val + "");
   $("" + sel + "").css("-o-" + prop + "", "" + val + "");
   $("" + sel + "").css("" + prop + "", "" + val + "");
}

function resetPoster() {


   prefixifyer(".dual_item, .dual_item img", "transition", "all .3s ease-out");

   $(".shader").css("opacity", 0);
   prefixifyer(".dual_item", "transform", "rotateY(0deg) rotateX(0deg)");
   prefixifyer(".layer_2 img, .layer_3 img, .layer_4 span", "transform", "translateX(0px) translateY(0px)");
   prefixifyer(".dual_item_container", "transform", "scale(1,1)");

}

function noTransition() {
   prefixifyer(".dual_item img, .dual_item", "transition", "all 0s ease-out");
}

$(document).ready(function () {

   if ($('html').hasClass('touch')) {
      $(".dual_item").bind('touchmove', function (e) {
         noTransition();
         e.preventDefault();
         var currentY = e.originalEvent.touches[0].clientY;
         var currentX = e.originalEvent.touches[0].clientX;
         if (start_y == null) {
            start_y = currentY
         }
         ;
         if (start_x == null) {
            start_x = currentX
         }
         ;
         newY = (start_y - currentY);
         newX = (start_x - currentX);
         prefixifyer(".dual_item", "transform", "rotateY(" + ((newX * .05) * -1) + "deg) rotateX(" + (newY * .05) + "deg)");
         newX = (start_y - currentY) * -.025;
         newY = (start_x - currentX) * .025;
         prefixifyer(".layer_2 img", "transform", "translateX(" + ((newY * .25) * -1) + "px) translateY(" + (newX * .25) + "px)");
         prefixifyer(".layer_3 img", "transform", "translateX(" + ((newY * .5) * -1) + "px) translateY(" + (newX * .5) + "px)");
         prefixifyer(".layer_4 span", "transform", "translateX(" + ((newY * .8) * -1) + "px) translateY(" + ((newX * .8)) + "px)");


         //    $(".shader").css("opacity","1");

         // if(newX < 0){
         //  gloss = (newX*.25) * -.5;
         //    if(gloss > .40){
         //      gloss = .4;
         //    }

         //  angle = newY;
         //  if(angle >= 5){
         //    angle = 5;
         //  }
         //  if(angle <= -5){
         //    angle = -5;
         //  }

         //  $(".shader").css("background","linear-gradient("+(180+(angle*-10))+"deg, rgba(255,255,255,.0), rgba(255,255,255,"+gloss+"))");
         // }

         // if(newX > 0){

         //  shade = (newX*.1);
         //  if(shade > .20){
         //    shade = .2;
         //  }
         //  angle = newY;
         //  if(angle >= 5){
         //    angle = 5;
         //  }
         //  if(angle <= -5){
         //    angle = -5;
         //  }

         //  $(".shader").css("background","linear-gradient("+(180+(angle*10))+"deg, rgba(0,0,0,"+shade+"), rgba(255,255,255,.0))");

         // }


      });

      $(".dual_item").bind('touchend', function (e) {
         e.preventDefault();
         start_x = null;
         start_y = null;
         resetPoster();
      });
   }

   if ($('html').hasClass('no-touch')) {

      $(document).mousemove(function (e) {

         //on mousemove, make the poster scale up a bit
         if ($('html').hasClass('no-touch')) {
            prefixifyer(".dual_item_container", "transform", "scale(1.1,1.1)");
         }

         //get the center of the dual_item/window
         var position = $(".dual_item").position();
         logoCenterX = $(window).width() / 2;
         logoCenterY = $(window).height() / 2;

         //get current coordinates
         var y = e.pageY;
         var x = e.pageX;

         //for rotation
         xRotate = (x - logoCenterX) * .02;
         yRotate = (logoCenterY - y) * .02;

         $(".shader").css("opacity", "1");

         if (yRotate < 0) {
            gloss = (yRotate * .25) * -.5;
            if (gloss > .40) {
               gloss = .4;
            }

            angle = xRotate;
            if (angle >= 5) {
               angle = 5;
            }
            if (angle <= -5) {
               angle = -5;
            }

            $(".shader").css("background", "linear-gradient(" + (180 + (angle * -10)) + "deg, rgba(255,255,255,.0), rgba(255,255,255," + gloss + "))");
         }

         if (yRotate > 0) {
            shade = (yRotate * .1);
            if (shade > .20) {
               shade = .2;
            }
            angle = xRotate;
            if (angle >= 5) {
               angle = 5;
            }
            if (angle <= -5) {
               angle = -5;
            }

            $(".shader").css("background", "linear-gradient(" + (180 + (angle * 10)) + "deg, rgba(0,0,0," + shade + "), rgba(255,255,255,.0))");

         }


         //remove transitions while animating  
         noTransition();

         // rotate the whole dual_item    
         prefixifyer(".dual_item", "transform", "rotateY(" + (xRotate * -1) + "deg) rotateX(" + (yRotate * -1) + "deg)");


         //shift individutal layers on X,Y axis for parallax effect
         prefixifyer(".layer_2 img", "transform", "translateX(" + ((xRotate * .25) * -1) + "px) translateY(" + (yRotate * .25) + "px)");
         prefixifyer(".layer_3 img", "transform", "translateX(" + ((xRotate * .3) * -1) + "px) translateY(" + (yRotate * .3) + "px)");
         prefixifyer(".layer_4 span", "transform", "translateX(" + ((xRotate * .5) * -1) + "px) translateY(" + (yRotate * .5) + "px)");

      });

      $(document).mouseleave(function () {
         resetPoster();
      });

   }

   //change the active dual_item
   $(".dual_item_sm").click(function () {
      if ($(this).hasClass('active') == 0) {
         $(this).toggleClass('active');
         $(this).siblings().removeClass('active');
         var show = ($(this).data("dual_item"));
         $(".active_dual_item").toggleClass("active_dual_item");
         $('#' + show + '').addClass("active_dual_item");

      }
   })
});
//
//// JEDITABLE
//
//
//$(function() {
//        
//  $(".click").editable("example.php", { 
//      indicator : "<img src='img/indicator.gif'>",
//      tooltip   : "Click to edit...",
//      style  : "inherit"
//  });
// 
//  
//  $(".click2").editable("example.php", { 
//      indicator : "<img src='img/indicator.gif'>",
//      type   : 'textarea',
//      tooltip   : "Click to edit...",
//      style  : "inherit"
//  });
//   $(".editable_select").editable("example.php", { 
//    indicator : '<img src="img/indicator.gif">',
//    data   : "{'Lorem ipsum':'Lorem ipsum','Ipsum dolor':'Ipsum dolor','Dolor sit':'Dolor sit'}",
//    type   : "select",
//    style  : "inherit",
//    submitdata : function() {
//      return {id : 2};
//    }
//  });
// 
//  /* Should not cause error. */
//  $("#nosuch").editable("example.php", { 
//      indicator : "<img src='img/indicator.gif'>",
//      type   : 'textarea',
//      submit : 'OK'
//  });
//
//});