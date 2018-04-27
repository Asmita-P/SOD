define(["jquery", "bootstrap"], function($) {
	$(function() {
		window.singleSelectClicked = function singleSelectClicked(formId) {
			$('#'+formId+" .testOptions").find('input[type="checkbox"]').each(function(index, element) {
				var elem = $(element);
				elem.replaceWith(
				$("<input>",{
				    type:'radio',
				    name: elem.attr('name'),
				    value: elem.val()
				}));
			});
		}
		window.multipleSelectClicked = function multipleSelectClicked(formId) {
			$('#'+formId+" .testOptions").find('input[type="radio"]').each(function(index, element) {
				var elem = $(element);
				elem.replaceWith(
				$("<input>",{
				    type:'checkbox',
				    name: elem.attr('name'),
				    value: elem.val()
				}));
			});
		}

	});
});