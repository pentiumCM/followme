<template>
	<div class="layout" id="layout-header">
		<layout>
			<i-header>
				<i-menu mode="horizontal" theme="dark" active-name="1">
					<div class="layout-logo-img"><img src="/layout/img/jitrjgc.png"  /></div>
					<!--<div class="layout-logo">&nbsp;&nbsp;<span>工程教育认证</span></div>-->
					<div class="layout-nav">

						<span>欢迎您</span>
						<a href="javascript:void(0);">管理员</a>
						<span class="nowTime">{{realTime}}</span>
					</div>
				</i-menu>
			</i-header>
		</layout>
	</div>
</template>
	<script type="text/javascript" src="/layout/js/layout-header.js"></script>