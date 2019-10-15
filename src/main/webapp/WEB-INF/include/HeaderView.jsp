<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>apples from Taras</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicons -->
<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">
<link rel="apple-touch-icon" href="img/icon.png">

<!-- ************************* CSS Files ************************* -->

<!-- Vendor CSS -->
<link rel="stylesheet" href="<c:url value="/resources/css/vendor.css"/>" />

<!-- style css -->
<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
</head>

<body>
	<!-- Main Wrapper Start -->
	<div class="wrapper">
		<!-- Header Start -->
		<header class="header">
			<div class="header__inner fixed-header">
				<div class="header__main">
					<div class="container-fluid">
						<div class="row">
							<div class="col-12">
								<div class="header__main-inner">
									<div class="header__main-left">
										<div class="logo">
											<a href="index.html" class="logo--normal"> <img
												src="img/logo/logo.png" alt="Logo">
											</a>
										</div>
									</div>
									<div class="header__main-center">
										<nav class="main-navigation text-center d-none d-lg-block">
											<%@ include file="/WEB-INF/include/MainMenuView.jsp"%>
										</nav>
									</div>
									<div class="header__main-right">
										<div class="header-toolbar-wrap">
											<div class="header-toolbar">
												<div class="header-toolbar__item d-block d-lg-none">
													<a href="#offcanvasMenu"
														class="header-toolbar__btn toolbar-btn menu-btn">
														<div class="hamburger-icon">
															<span></span> <span></span> <span></span> <span></span> <span></span>
															<span></span>
														</div>
													</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</header>
		<!-- Breadcrumb area Start -->
		<section class="page-title-area bg-image ptb--80"
			data-bg-image="img/bg/page_title_bg.jpg"></section>
		<!-- Breadcrumb area End -->
		<!-- Header End -->