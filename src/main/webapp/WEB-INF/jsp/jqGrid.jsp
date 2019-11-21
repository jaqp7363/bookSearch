<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#list").jqGrid({
			url:"/book/keywordSearchBook",
			datatype: 'local',
			mtype:'POST',
			jsonReader : {
				root : "documents"
			},
			postData: {
				query:$("#keyword").val()
			},
			colNames:['도서 제목', 'thumbnail', 'contents', 'isbn', 'authors',
					'출판사', 'datetime', '정가', '판매가'],
			colModel:[
				{name:"title",		sortable:false, width:"40%"},
				{name:"thumbnail",	sortable:false, hidden:true},
				{name:"contents",	sortable:false, hidden:true},
				{name:"isbn", 		sortable:false, hidden:true},
				{name:"authors", 	sortable:false, hidden:true},
				{name:"publisher",	sortable:false, width:"20%"},
				{name:"datetime",	sortable:false, hidden:true},
				{name:"price", 		sortable:false, width:"20%", formetter:numberWithCommas},
				{name:"sale_price", sortable:false, width:"20%", formetter:numberWithCommas}
			],
			caption : 'Book List',
			height: 235,
			rowNum: 10,
			viewrecords: true,
			autowidth: true,
			headertitles: true,
			emptyrecords: '데이터가 없습니다.',
			multiboxonly: true,
			viewsortcols: [true,'vertical',true],
			loadComplete : function(data){
				console.log(data);
				if(data.total != 0) {
					console.log(data.meta.total_count);
					initPage("list", data.meta.total_count);
				}
				var rows = $('#list').jqGrid('getGridParam', 'records');
				$('#NoData').html("");
				if(rows == 0) $("#NoData").html("현재 검색된 도서가 없습니다.");
				mostSearchList();
			},
			onSelectRow : function(rowId, status, e){
				var w = 900;
				var h = 600;
				var left = $(window).scrollLeft() + ($(window).width() - w) / 2;
				var top = $(window).scrollTop() + ($(window).height() - h) / 2;
				$('.popup_box').css({
					'width' : w,
					'height' : h,
					'top' : top,
					'left' : left
				});
				$('#all_popup').show();
				
				var title = $('#list').getCell(rowId, 'title');
				var thumbnail = $('#list').getCell(rowId, 'thumbnail');
				var contents = $('#list').getCell(rowId, 'contents');
				var isbn = $('#list').getCell(rowId, 'isbn');
				var authors = $('#list').getCell(rowId, 'authors');
				var publisher = $('#list').getCell(rowId, 'publisher');
				var datetime = $('#list').getCell(rowId, 'datetime');
				var price = $('#list').getCell(rowId, 'price');
				var sale_price = $('#list').getCell(rowId, 'sale_price');
				$('#layerIframe').attr('src', '/bookDetail?title='+encodeURI(title)+
						"&thumbnail="+thumbnail+
						"&contents="+encodeURI(contents)+
						"&isbn="+isbn+
						"&authors="+encodeURI(authors)+
						"&publisher="+encodeURI(publisher)+
						"&datetime="+datetime+
						"&price="+price+
						"&sale_price="+sale_price);
			}
		});
	});
	function numberWithCommas(value) {
		var str = 0;
		if (value !== '' && value !== null && typeof value !== "undefined") {
			str = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		return str;
	}
	function initPage(gridId, totalSize) {
		var pageCount = 10;
		var totalPage = Math.ceil(totalSize/$("#"+gridId).getGridParam('rowNum'));
		var totalPageList = Math.ceil(totalPage/pageCount);
		var pageList = Math.ceil(currentPage/pageCount);
		
		if(pageList<1) pageList=1;
		if(pageList>totalPageList) pageList = totalPageList;
		var startPageList = ((pageList-1)*pageCount)+1;
		var endPageList = startPageList + pageCount-1;
		
		if(startPageList<1)startPageList = 1;
		if(endPageList>totalPage)endPageList=totalPage;
		if(endPageList<1) endPageList = 1;
		
		var pageInner="";
		
		pageInner += "<a class='first' href='javascript:firstPage()'><img src='/resources/image/ico-first.png'></a>&nbsp;";
		pageInner += "<a class='pre' href='javascript:prePage("+totalSize+")'><img src='/resources/image/ico-prev.png'></a>&nbsp";
		for(var i=startPageList; i<=endPageList; i++) {
			if(i == currentPage) {
				pageInner += "<a href='javascript:goPage("+(i)+")' id='"+(i)+"'><strong>"+(i)+"</strong></a>&nbsp";
			}else {
				pageInner += "<a href='javascript:goPage("+(i)+")' id='"+(i)+"'>"+(i)+"</a>&nbsp";
			}
		}
		pageInner += "<a class='next' href='javascript:nextPage("+totalSize+")'><img src='/resources/image/ico-next.png'></a>&nbsp";
		pageInner += "<a class='next' href='javascript:lastPage("+totalSize+")'><img src='/resources/image/ico-last.png'></a>&nbsp";
		
		$("#paginate").html("");
		$("#paginate").append(pageInner);
	}
	function goPage(move) {
		currentPage = move;
		$("#list").setGridParam({
			datatype: 'json',
			postData : {
				query:$("#keyword").val(),
				currentPage:currentPage
			}
		}).trigger("reloadGrid");
	}
	function firstPage() {
		$("#searchBtn").trigger('click');
	}
	function prePage(totalSize) {
		if(currentPage == 1) return;
		currentPage -= 1;
		goPage(currentPage);
	}
	function nextPage(totalSize) {
		var maxPage = Math.floor(totalSize/10)+1;
		if(maxPage==currentPage) return;
		currentPage += 1;
		goPage(currentPage);
	}
	function lastPage(totalSize) {
		var maxPage = Math.floor(totalSize/10)+1;
		if(maxPage == currentPage) return;
		currentPage = maxPage;
		console.log(currentPage);
		goPage(currentPage);
	}
</script>