document.getElementById('com:bm').addEventListener('click', function() {
	var mathFieldSpan = document.getElementById("math-field");
	var latexInput = document.getElementById('mat:hid');
	var MQ = MathQuill.getInterface(2);
	var mathField = MQ.MathField(mathFieldSpan, {
		spaceBehavesLikeTab: true,
		handlers: {
			edit: function() {
				var latex = mathField.latex();
				latexInput.value = latex;
			},
		},
	});
});