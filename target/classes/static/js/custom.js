/* JS Document */

/******************************

[Table of Contents]

1. Vars and Inits
2. Set Header
3. Init Menu
4. Init Timer
5. Init Favorite
6. Init Fix Product Border
7. Init Isotope Filtering
8. Init Slider


******************************/

jQuery(document).ready(function($)
{
	"use strict";

	/* 

	1. Vars and Inits

	*/

	var header = $('.header');
	var topNav = $('.top_nav')
	var mainSlider = $('.main_slider');
	var hamburger = $('.hamburger_container');
	var menu = $('.hamburger_menu');
	var menuActive = false;
	var hamburgerClose = $('.hamburger_close');
	var fsOverlay = $('.fs_menu_overlay');

    /*

	Urls

	*/
    var urlListProduct  =   '/list-product';
    var urlLogin        =   '/login-with-ajax';
    var urlSignup       =   '/signup-with-ajax';
    var urlRare         =   '/rate';
    var urlSendCode     =   '/forgot-password/send-code';
    var urlForgotPass   =   '/forgot-password';
    var urlFeedback   =   '/feedback';



	setHeader();

	$(window).on('resize', function()
	{
		initFixProductBorder();
		setHeader();
	});

	$(document).on('scroll', function()
	{
		setHeader();
	});

	initMenu();
	initTimer();
	initFavorite();
	initFixProductBorder();
	initIsotopeFiltering();
	initSlider();
	initMenuCategory();
	initListBrand();

//    async function fillListProductByCategory(id) {
//      let blockList = $('.list_product_by_category');
//      blockList.empty();
//      try {
//        let listProduct = await getListProductByCategoryId(id);
//        let html = '';
//        listProduct.forEach(item => {
//          html += `<li><a href="#">${item.name}</a></li>`;
//        });
//        blockList.html(html);
//      } catch (error) {
//        console.error(error);
//      }
//    }

//    function getListProductByCategoryId(id){
//        let urlGetByCategoryId = "/category";
//        return callAjaxPromise(urlGetByCategoryId, "GET", {id : id})
//    }


    //login
    $('#submit_modal_login').on('click', ()=>{
        let username = $('#email_modal_login').val();
        let password = $('#pass_modal_login').val();

        $(".account_not_found_message").hide();
        $(".invalid_password_message").hide();
        $.ajax({
              url: urlLogin,
              method: 'POST',
              data: {username : username, password : password}
            }).then(function(response) {
                 if(response === 'OK'){
                    window.location.href = "/"
                 }else if(response === 'NOT_FOUND'){
                    $(".account_not_found_message").show();
                 }else if(response === 'INVALID'){
                    $(".invalid_password_message").show();
                 }
            }).fail(function(error) {
              console.log("error : " + error);
            });
            localStorage.removeItem('listCartItemSelected');
            localStorage.removeItem('totalPay');
    });

    //sign up
    $('#btn_signup').on('click', ()=>{
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        let fullName = $('#name_signup').val();
        let email = $('#email_signup').val();
        let password = $('#password_signup').val();
        let re_password = $('#re_password_signup').val();

        $(".error_email_signup").hide();
        $(".msg_pass_not_match").hide();

        if(!emailPattern.test(email)){
            $(".error_email_signup").text('Định dạng email không hợp lệ!');
            $(".error_email_signup").show();
            return;
        } else if(password !== re_password){
            $(".msg_pass_not_match").show();
            return;
        }else{
            let dataToSend =  {
                                  email : email,
                                  password : password,
                                  fullName : fullName
                              }
             $.ajax({
                  url: urlSignup,
                  method: 'POST',
                  contentType:"application/json; charset=utf-8",
                  data : JSON.stringify(dataToSend),
                  dataType: "text",
            }).then(function(response) {
                 if(response === 'OK'){
                  /*  $("#my-Register").hide();*/
                    $(".account_not_found_message").hide();
                    $(".invalid_password_message").hide();
                    $('#email_modal_login').val("");
                    $('#pass_modal_login').val("");
                    window.location.hash = "my-Login";
                 }else if(response === 'ALREADY_EXIST'){
                    $(".error_email_signup").text('Tài khoản đã tồn tại!');
                    $(".error_email_signup").show();
                 }
            }).fail(function(error) {
              console.log("error : " + error);
            });
        }
    });

    /* forgot password*/
    $('#btnSendCode').on('click', async ()=>{
        let email = $('#email_forgot').val();

        let response = await callAjaxPromise(urlSendCode,'POST', {email : email});
        if(response === 'OK'){
            $('.msg_not_exist').hide();
            $('.msg_sent_code').show();
        }else if(response === 'NOT FOUND'){
            $('.msg_sent_code').hide();
            $('.msg_not_exist').show();
        }
    });

    $('#btn_forgotSubmit').on('click', async ()=>{
        let email                = $('#email_forgot').val();
        let code_confirm         = $('#code_confirm').val();
        let new_password         = $('#new_password').val();
        let re_password_forgot   = $('#re_password_forgot').val();

        if(new_password !== re_password_forgot){
            $('.msg_pass_not_match').show();
        }else{
            $('.msg_pass_not_match').hide();
        }
        let data = {
            email   : email,
            code    : code_confirm,
            newPass : new_password
        }
        let response = await callAjaxPromise(urlForgotPass,'POST', data);

        if(response === 'OK'){
            window.location.hash = "my-Login";
        }else if(response === 'NOT MATCH'){
            $('.msg_sent_code').text('Mã xác nhận không khớp!');
            $('.msg_sent_code').show();
        }
    });

    $('#logout').on('click', function(){
         localStorage.clear();
    })
    /* end forgot password*/


	/*
	2. Set Header
	*/

	function setHeader()
	{

		if(window.innerWidth > 991 && menuActive)
		{
			closeMenu();
		}
	}

	/* 

	3. Init Menu

	*/

	function initMenu()
	{
		if(hamburger.length)
		{
			hamburger.on('click', function()
			{
				if(!menuActive)
				{
					openMenu();
				}
			});
		}

		if(fsOverlay.length)
		{
			fsOverlay.on('click', function()
			{
				if(menuActive)
				{
					closeMenu();
				}
			});
		}

		if(hamburgerClose.length)
		{
			hamburgerClose.on('click', function()
			{
				if(menuActive)
				{
					closeMenu();
				}
			});
		}

		if($('.menu_item').length)
		{
			var items = document.getElementsByClassName('menu_item');
			var i;

			for(i = 0; i < items.length; i++)
			{
				if(items[i].classList.contains("has-children"))
				{
					items[i].onclick = function()
					{
						this.classList.toggle("active");
						var panel = this.children[1];
					    if(panel.style.maxHeight)
					    {
					    	panel.style.maxHeight = null;
					    }
					    else
					    {
					    	panel.style.maxHeight = panel.scrollHeight + "px";
					    }
					}
				}	
			}
		}
	}

	function openMenu()
	{
		menu.addClass('active');
		// menu.css('right', "0");
		fsOverlay.css('pointer-events', "auto");
		menuActive = true;
	}

	function closeMenu()
	{
		menu.removeClass('active');
		fsOverlay.css('pointer-events', "none");
		menuActive = false;
	}

	/* 

	4. Init Timer

	*/

	function initTimer()
    {
    	if($('.timer').length)
    	{
    		// Uncomment line below and replace date
	    	// var target_date = new Date("Dec 7, 2017").getTime();

	    	// comment lines below
	    	var date = new Date();
	    	date.setDate(date.getDate() + 3);
	    	var target_date = date.getTime();
	    	//----------------------------------------
	 
			// variables for time units
			var days, hours, minutes, seconds;

			var d = $('#day');
			var h = $('#hour');
			var m = $('#minute');
			var s = $('#second');

			setInterval(function ()
			{
			    // find the amount of "seconds" between now and target
			    var current_date = new Date().getTime();
			    var seconds_left = (target_date - current_date) / 1000;
			 
			    // do some time calculations
			    days = parseInt(seconds_left / 86400);
			    seconds_left = seconds_left % 86400;
			     
			    hours = parseInt(seconds_left / 3600);
			    seconds_left = seconds_left % 3600;
			     
			    minutes = parseInt(seconds_left / 60);
			    seconds = parseInt(seconds_left % 60);

			    // display result
			    d.text(days);
			    h.text(hours);
			    m.text(minutes);
			    s.text(seconds); 
			 
			}, 1000);
    	}	
    }

    /* 

	5. Init Favorite

	*/

    function initFavorite()
    {
    	if($('.favorite').length)
    	{
    		var favs = $('.favorite');

    		favs.each(function()
    		{
    			var fav = $(this);
    			var active = false;
    			if(fav.hasClass('active'))
    			{
    				active = true;
    			}

    			fav.on('click', function()
    			{
    				if(active)
    				{
    					fav.removeClass('active');
    					active = false;
    				}
    				else
    				{
    					fav.addClass('active');
    					active = true;
    				}
    			});
    		});
    	}
    }

    /* 

	6. Init Fix Product Border

	*/

    function initFixProductBorder()
    {
    	if($('.product_filter').length)
    	{
			var products = $('.product_filter:visible');
    		var wdth = window.innerWidth;

    		// reset border
    		products.each(function()
    		{
    			$(this).css('border-right', 'solid 1px #e9e9e9');
    		});

    		// if window width is 991px or less

    		if(wdth < 480)
			{
				for(var i = 0; i < products.length; i++)
				{
					var product = $(products[i]);
					product.css('border-right', 'none');
				}
			}

    		else if(wdth < 576)
			{
				if(products.length < 5)
				{
					var product = $(products[products.length - 1]);
					product.css('border-right', 'none');
				}
				for(var i = 1; i < products.length; i+=2)
				{
					var product = $(products[i]);
					product.css('border-right', 'none');
				}
			}

    		else if(wdth < 768)
			{
				if(products.length < 5)
				{
					var product = $(products[products.length - 1]);
					product.css('border-right', 'none');
				}
				for(var i = 2; i < products.length; i+=3)
				{
					var product = $(products[i]);
					product.css('border-right', 'none');
				}
			}

    		else if(wdth < 992)
			{
				if(products.length < 5)
				{
					var product = $(products[products.length - 1]);
					product.css('border-right', 'none');
				}
				for(var i = 3; i < products.length; i+=4)
				{
					var product = $(products[i]);
					product.css('border-right', 'none');
				}
			}

			//if window width is larger than 991px
			else
			{
				if(products.length < 5)
				{
					var product = $(products[products.length - 1]);
					product.css('border-right', 'none');
				}
				for(var i = 4; i < products.length; i+=5)
				{
					var product = $(products[i]);
					product.css('border-right', 'none');
				}
			}	
    	}
    }

    /* 

	7. Init Isotope Filtering

	*/

    function initIsotopeFiltering()
    {
    	if($('.grid_sorting_button').length)
    	{
    		$('.grid_sorting_button').click(function()
	    	{
	    		// putting border fix inside of setTimeout because of the transition duration
	    		setTimeout(function()
		        {
		        	initFixProductBorder();
		        },500);

		        $('.grid_sorting_button.active').removeClass('active');
		        $(this).addClass('active');
		 
		        var selector = $(this).attr('data-filter');
		        $('.product-grid').isotope({
		            filter: selector,
		            animationOptions: {
		                duration: 750,
		                easing: 'linear',
		                queue: false
		            }
		        });

		        
		         return false;
		    });
    	}
    }

    /* 

	8. Init Slider

	*/

    function initSlider()
    {
    	if($('.product_slider').length)
    	{
    		var slider1 = $('.product_slider');

    		slider1.owlCarousel({
    			loop:false,
    			dots:false,
    			nav:false,
    			autoplaySpeed:1000,
                items:2,
                autoplay:true,
    			responsive:
				{
					0:{items:2},
					480:{items:2},
					768:{items:3},
					991:{items:4},
					1280:{items:5},
					1440:{items:5}
				}
    		});

    		if($('.product_slider_nav_left').length)
    		{
    			$('.product_slider_nav_left').on('click', function()
    			{
    				slider1.trigger('prev.owl.carousel');
    			});
    		}

    		if($('.product_slider_nav_right').length)
    		{
    			$('.product_slider_nav_right').on('click', function()
    			{
    				slider1.trigger('next.owl.carousel');
    			});
    		}
    	}
    }

	function initMenuCategory(){
		var owl = $('.category__slider');
		owl.owlCarousel({
			loop:false,
			dots:false,
			rewindNav : true,
			autoWidth:true
		});

		// Go to the next item
		$('.category_slider_nav_right').on('click', function() {
			owl.trigger('next.owl.carousel');
		})
		// Go to the previous item
		$('.category_slider_nav_left').on('click', function() {
			owl.trigger('prev.owl.carousel');
		})

	}

	function initListBrand(){
		var owl = $('.list__brand-slider');
		owl.owlCarousel({
			loop:false,
			dots:false,
			rewindNav : true,
			margin: 6,
			autoWidth:true,
			loop:true,
            autoplaySpeed: 1000,
            items:3,
            autoplay:true
		});

		// Go to the next item
		$('.list_brand_slider_left').on('click', function() {
			owl.trigger('prev.owl.carousel');
		})
		// Go to the previous item
		$('.list_brand_slider_right').on('click', function() {
			owl.trigger('next.owl.carousel');
		})
	}
	/*
	Call ajax
	*/
	function callAjax(method, url, data){
            $.ajax({
              url: url,
              method: method,
              data: data
            }).then(function(response) {

            }).fail(function(error) {
              console.log("error : " + error);
            });
        }
	function callAjaxPromise(url, method, data) {
        return new Promise((resolve, reject) => {
          $.ajax({
            url: url,
            method: method,
            data: data
          }).then(function(response) {
            resolve(response);
          }).fail(function(error) {
            console.log("error : " + error);
            reject(error);
          });
        });
    }

});