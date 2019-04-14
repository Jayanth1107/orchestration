$(document).ready(function () {

	$('#scriptsform').submit(function (event) {
		event.preventDefault();

		var tdtags = document.getElementsByTagName('td');
		for(c=0; c<tdtags.length; c++){
			//console.log(tdtags[c].getAttribute('class'));
			if(tdtags[c].getAttribute('class')!=null && tdtags[c].getAttribute('class').includes('hoverdisplay')){
				alert('Please correct the highlighted errors');
				$('#response').html('Please correct the highlighted errors <br/> * also remove carriage return at EOF');
				return false;
			}
		}

		// show that something is loading
		$('#response').html("<b>executing...</b>");

		//var data = $this;
		// Call ajax for pass data to other place
		$.ajax({
			type: 'POST',
			url: '/uploadScripts',
			crossDomain: true,
			data: $('#scriptsform').serialize(), // getting filed value in serialize form
			success: function(data) { $('#response').html(data); },
			error: function(err) { alert('Failed! reason: '+ err); },
			//beforeSend: setHeader
		});

		// to prevent refreshing the whole page page
		return false;

	});
	// function setHeader(xhr) {
	//xhr.setRequestHeader('Authorization', 'testToken');
	// }

	$("#message").bind('input propertychange', function () {
		const selection1 = ['Y', 'Y', 'Y', 'N', 'F'];
		const selection2 = ['Y', 'Y', 'Y', 'Y', 'N', 'F'];
		//var dateRegex = "/^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/";
		var pattern = new RegExp("^((0?[1-9]|1[012])[/](0?[1-9]|[12][0-9]|3[01])[/](19|20)?[0-9]{2})*$");

		var table = document.getElementById('result1');

		table.innerHTML = "";

		var header = document.createElement('thead');
		header.setAttribute('id','resultHeader');
		var col1 = document.createElement('td');
		col1.innerHTML = 'column1';
		//col1.style.borderWidth='1px';
		var col2 = document.createElement('td');
		col2.innerHTML = 'column2';
		//col2.style.borderWidth='1px';
		var col3 = document.createElement('td');
		col3.innerHTML = 'grouping_method';
		//col3.style.borderWidth='1px';
		var col4 = document.createElement('td');
		col4.innerHTML = 'comments_text';
		//col4.style.borderWidth='1px';
		var col5 = document.createElement('td');
		col5.innerHTML = 'mod_date';
		//col5.style.borderWidth='1px';

		header.appendChild(col1);
		header.appendChild(col2);
		header.appendChild(col3);
		header.appendChild(col4);
		header.appendChild(col5);
		//table.appendChild(header);
		table.append(header);

		var rows = $('#message').val().split('\n');

		for (i = 0; i < rows.length; i++) {

			var row = document.createElement('tr');
			var cols = rows[i].split('\t');

			for (j = 0; j < cols.length; j++) {

				var column = document.createElement('td');
				column.innerHTML = cols[j];

				if (cols[j].length == 0 && selection1[j] == 'Y') {
					//console.log(selection1[j]);
					column.style.borderColor = 'red';
					column.setAttribute('class', 'hoverdisplay');

					var missingfield = document.createElement('span');
					missingfield.setAttribute('class', 'missingfield');
					missingfield.innerHTML = 'mandatory field';

					column.append(missingfield);
					//$(this).css("background-color", "#ffffff");
				}

				if (selection1[j] == 'F' && !cols[j].match(pattern)) {
					column.style.borderColor = 'red';
					column.setAttribute('class', 'hoverdisplay');

					var dateformat = document.createElement('span');
					dateformat.setAttribute('class', 'dateformat');
					dateformat.innerHTML = 'date format - MM/DD/YYYY';

					column.append(dateformat);
					//$(this).css("background-color", "#ffffff");
				}

				row.appendChild(column);
			}

			table.appendChild(row);
			//console.log(table);
			if ($('#message').val().length == 0) {
				$('#result1').remove();
			}
		}
	});

	// $.fn.autogrow = function() {
	//     return this.each(function() {
	//         var textarea = this;
	//         $.fn.autogrow.resize(textarea);
	//         $(textarea).focus(function() {
	//             textarea.interval = setInterval(function() {
	//                 $.fn.autogrow.resize(textarea);
	//             }, 500);
	//         }).blur(function() {
	//             clearInterval(textarea.interval);
	//         });
	//     });
	// };
	// $.fn.autogrow.resize = function(textarea) {
	//     var lineHeight = parseInt($(textarea).css('line-height'), 10);
	//     var lines = textarea.value.split('\n');
	//     var columns = textarea.cols;
	//     var lineCount = 0;
	//     $.each(lines, function() {
	//         lineCount += Math.ceil(this.length / columns) || 1;
	//     });
	//     var height = lineHeight * (lineCount + 1);
	//     $(textarea).css('height', height);
	// };


	// $('#message').autogrow();

//	$("button:#Get").click(function () {

//	$('#msg').html($('textarea#message').val());

//	});

//	$("button:#Reset").click(function () {

//	$('#msg').html("");
//	$('textarea#message').val("");

//	});

//	$("button:#Set").click(function () {

//	$('textarea#message').val("ABC");
//	$('#msg').html($('input:textbox').val());

//	});

});