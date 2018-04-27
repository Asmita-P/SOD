<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="header.jsp"%>
<style>
body, html {
      height: 100%;
      background-repeat: no-repeat;    /*background-image: linear-gradient(rgb(12, 97, 33),rgb(104, 145, 162));*/
      background:black;
      position: relative;
}
#login-box {
      position: absolute;
      top: 0px;
      left: 50%;
      transform: translateX(-50%);
      width: 350px;
      margin: 0 auto;
      border: 1px solid black;
      background: rgba(48, 46, 45, 1);
      min-height: 250px;
      padding: 20px;
      z-index: 9999;
}
#login-box .logo .logo-caption {
      font-family: 'Poiret One', cursive;
      color: white;
      text-align: center;
      margin-bottom: 0px;
}
#login-box .logo .tweak {
      color: #ff5252;
}
#login-box .controls {
      padding-top: 30px;
}
#login-box .controls input {
      border-radius: 0px;
      background: rgb(98, 96, 96);
      border: 0px;
      color: white;
      font-family: 'Nunito', sans-serif;
}
#login-box .controls input:focus {
      box-shadow: none;
}
#login-box .controls input:first-child {
      border-top-left-radius: 2px;
      border-top-right-radius: 2px;
}
#login-box .controls input:last-child {
      border-bottom-left-radius: 2px;
      border-bottom-right-radius: 2px;
}
#login-box button.btn-custom {
      border-radius: 2px;
      margin-top: 8px;
      background:#ff5252;
      border-color: rgba(48, 46, 45, 1);
      color: white;
      font-family: 'Nunito', sans-serif;
}
#login-box button.btn-custom:hover{
      -webkit-transition: all 500ms ease;
      -moz-transition: all 500ms ease;
      -ms-transition: all 500ms ease;
      -o-transition: all 500ms ease;
      transition: all 500ms ease;
      background: rgba(48, 46, 45, 1);
      border-color: #ff5252;
}
#particles-js{
      width: 100%;
      height: 100%;
      background-size: cover;
      background-position: 50% 50%;
      position: fixed;
      top: 0px;
      z-index:1;
}</style>
<body style="  background-image: url('https://cdn.wallpaper.com/main/legacy/gallery/17056190/21-Graduate-Directory-2015-Design.jpg'); background-repeat: no-repeat; background-size:cover;">

	<div class="container" >
		       <form method="post" id="passwordForm" action="/changePassword" >
                  <div id="login-box">
            <div class="logo">
                 
                  <h1 class="logo-caption"><span class="tweak">Forgot </span> Password.</h1>
            </div>
                        <div class="input-group">
                         		 <span class="input-group-addon"></span>
                              	<input type="text" class="input-lg form-control" name="username"
								placeholder="Username" autocomplete="off" autofocus> 
                        </div>
                        <div class="input-group">
                              <span class="input-group-addon"></span>
                              <input type="password" class="input-lg form-control" name="password"
                                    placeholder="Password">
                        </div>
                        <div class="input-group">
                        	<span id="8char" class="glyphicon glyphicon-remove"
										style="color: #FF0004;"></span> 8 Characters Long<br> <span
										id="ucase" class="glyphicon glyphicon-remove"
										style="color: #FF0004;"></span> One Uppercase Letter
                      
                     </div>
                     <div class="input-group">
                     	<span id="lcase" class="glyphicon glyphicon-remove"
										style="color: #FF0004;"></span> One Lowercase Letter<br>
									<span id="num" class="glyphicon glyphicon-remove"
										style="color: #FF0004;"></span> One Number
                     </div>
                     <div class="input-group">
                      <span class="input-group-addon"></span>
                     <input type="password" class="input-lg form-control"
								name="password2" id="password2" placeholder="Repeat Password"
								autocomplete="off">
								</div>
                 <div class="row">
								<div class="col-sm-12">
									<span id="pwmatch" class="glyphicon glyphicon-remove"
										style="color: #FF0004;"></span> Passwords Match
								</div>
							</div>
							<input type="submit"
								class="col-xs-12 btn btn-primary btn-load btn-lg"
								data-loading-text="Changing Password..." value="Change Password">
                  </div>
            </form>
	
		</div>
		
<div id="particles-js"></div>
</body>
<script>
	$("input[type=password]").keyup(function() {
		var ucase = new RegExp("[A-Z]+");
		var lcase = new RegExp("[a-z]+");
		var num = new RegExp("[0-9]+");

		if ($("#password1").val().length >= 8) {
			$("#8char").removeClass("glyphicon-remove");
			$("#8char").addClass("glyphicon-ok");
			$("#8char").css("color", "#00A41E");
		} else {
			$("#8char").removeClass("glyphicon-ok");
			$("#8char").addClass("glyphicon-remove");
			$("#8char").css("color", "#FF0004");
		}

		if (ucase.test($("#password1").val())) {
			$("#ucase").removeClass("glyphicon-remove");
			$("#ucase").addClass("glyphicon-ok");
			$("#ucase").css("color", "#00A41E");
		} else {
			$("#ucase").removeClass("glyphicon-ok");
			$("#ucase").addClass("glyphicon-remove");
			$("#ucase").css("color", "#FF0004");
		}

		if (lcase.test($("#password1").val())) {
			$("#lcase").removeClass("glyphicon-remove");
			$("#lcase").addClass("glyphicon-ok");
			$("#lcase").css("color", "#00A41E");
		} else {
			$("#lcase").removeClass("glyphicon-ok");
			$("#lcase").addClass("glyphicon-remove");
			$("#lcase").css("color", "#FF0004");
		}

		if (num.test($("#password1").val())) {
			$("#num").removeClass("glyphicon-remove");
			$("#num").addClass("glyphicon-ok");
			$("#num").css("color", "#00A41E");
		} else {
			$("#num").removeClass("glyphicon-ok");
			$("#num").addClass("glyphicon-remove");
			$("#num").css("color", "#FF0004");
		}

		if ($("#password1").val() == $("#password2").val()) {
			$("#pwmatch").removeClass("glyphicon-remove");
			$("#pwmatch").addClass("glyphicon-ok");
			$("#pwmatch").css("color", "#00A41E");
		} else {
			$("#pwmatch").removeClass("glyphicon-ok");
			$("#pwmatch").addClass("glyphicon-remove");
			$("#pwmatch").css("color", "#FF0004");
		}
	});
	
	$.getScript("https://cdnjs.cloudflare.com/ajax/libs/particles.js/2.0.0/particles.min.js", function(){
	    particlesJS('particles-js',
	      {
	        "particles": {
	          "number": {
	            "value": 80,
	            "density": {
	              "enable": true,
	              "value_area": 800
	            }
	          },
	          "color": {
	            "value": "#ffffff"
	          },
	          "shape": {
	            "type": "circle",
	            "stroke": {
	              "width": 0,
	              "color": "#000000"
	            },
	            "polygon": {
	              "nb_sides": 5
	            },
	            "image": {
	              "width": 100,
	              "height": 100
	            }
	          },
	          "opacity": {
	            "value": 0.5,
	            "random": false,
	            "anim": {
	              "enable": false,
	              "speed": 1,
	              "opacity_min": 0.1,
	              "sync": false
	            }
	          },
	          "size": {
	            "value": 5,
	            "random": true,
	            "anim": {
	              "enable": false,
	              "speed": 40,
	              "size_min": 0.1,
	              "sync": false
	            }
	          },
	          "line_linked": {
	            "enable": true,
	            "distance": 150,
	            "color": "#ffffff",
	            "opacity": 0.4,
	            "width": 1
	          },
	          "move": {
	            "enable": true,
	            "speed": 6,
	            "direction": "none",
	            "random": false,
	            "straight": false,
	            "out_mode": "out",
	            "attract": {
	              "enable": false,
	              "rotateX": 600,
	              "rotateY": 1200
	            }
	          }
	        },
	        "interactivity": {
	          "detect_on": "canvas",
	          "events": {
	            "onhover": {
	              "enable": true,
	              "mode": "repulse"
	            },
	            "onclick": {
	              "enable": true,
	              "mode": "push"
	            },
	            "resize": true
	          },
	          "modes": {
	            "grab": {
	              "distance": 400,
	              "line_linked": {
	                "opacity": 1
	              }
	            },
	            "bubble": {
	              "distance": 400,
	              "size": 40,
	              "duration": 2,
	              "opacity": 8,
	              "speed": 3
	            },
	            "repulse": {
	              "distance": 200
	            },
	            "push": {
	              "particles_nb": 4
	            },
	            "remove": {
	              "particles_nb": 2
	            }
	          }
	        },
	        "retina_detect": true,
	        "config_demo": {
	          "hide_card": false,
	          "background_color": "#b61924",
	          "background_image": "",
	          "background_position": "50% 50%",
	          "background_repeat": "no-repeat",
	          "background_size": "cover"
	        }
	      }
	    );
	 
	});
	
</script>
</html>