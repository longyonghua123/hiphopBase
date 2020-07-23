$(function() {
	function do_paging(page_no) {
		// console.log('page ' + page_no);
		$('#pageNum').val(page_no);
		$('#table-pagination').parents('form').submit();
	}

	function init_paging() {
		var PAGES_COUNT = 2;
		var page_no = parseInt($('#pageNum').val(), 10);
		var total_no = parseInt($('#totalNum').val(), 10);
		// console.log(page_no + '/' + total_no);

		$('#table-pagination').detach();
		var $ul = $('<ul id="table-pagination" class="pagination pull-right" ></ul>');

		var $li = $('<li></li>');
		var $a = $('<a href="javaScript:void(0)">首页</a>');
		if (page_no == 1) {
			$li.addClass('disabled');
		} else {
			$a.click(function() {
				do_paging(1);
			});
		}
		$ul.append($li.append($a));

		$li = $('<li></li>');
		$a = $('<a href="javaScript:void(0)">上一页</a>');
		if (page_no == 1) {
			$li.addClass('disabled');
		} else {
			$a.click(function() {
				do_paging(page_no - 1);
			});
		}
		$ul.append($li.append($a));

		var l = [page_no];

		var i;
		for (i = page_no - 1; i >= 1 && i >= page_no - PAGES_COUNT; i--) {
			l.unshift(i);
		}
		for (i = page_no + 1; i <= total_no && i <= page_no + PAGES_COUNT; i++) {
			l.push(i);
		}
		// console.log(l);

		var need_last_page = (l[l.length - 1] != total_no);
		while (l.length > 0) {
			var page = l.shift();
			$li = $('<li></li>');
			$a = $('<a href="javaScript:void(0)"></a>').text(page);
			if (page == page_no) {
				$li.addClass('active');
			} else {
				$a.click(function(page) {
					return function() {
						do_paging(page);
					};
				}(page));
			}
			$ul.append($li.append($a));
		}

		if (need_last_page) {
			$ul.append($('<li></li>').addClass('disabled').append($('<a href="javaScript:void(0)"></a>').text('...')));

			$ul.append($('<li></li>').append($('<a href="javaScript:void(0)"></a>').text(total_no).click(function() {
				do_paging(total_no);
			})));
		}

		$li = $('<li></li>');
		$a = $('<a href="javaScript:void(0)">下一页</a>');
		if (page_no == total_no) {
			$li.addClass('disabled');
		} else {
			$a.click(function() {
				do_paging(page_no + 1);
			});
		}
		$ul.append($li.append($a));

		$li = $('<li></li>');
		$a = $('<a href="javaScript:void(0)">尾页</a>');
		if (page_no == total_no) {
			$li.addClass('disabled');
		} else {
			$a.click(function() {
				do_paging(total_no);
			});
		}
		$ul.append($li.append($a));

		$ul.appendTo($('#pageinfo div'));
	}

	init_paging();
});
