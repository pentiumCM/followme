<template>
	<div id="layout-sider" class="layout">
		<layout>
			<sider hide-trigger>
				<sider hide-trigger :style="{background: '#fff'}">
					<i-menu :active-name="activeName" theme="light" width="auto" :open-names="['1']" @on-select="onSelectMenu">
						<submenu name="1">
							<template slot="title">
								<icon type="ios-navigate"></icon>
								管理 1
							</template>
							<menu-item name="/followme/club/activityList.html" >活动管理</menu-item>
							<menu-item name="/followme/club/activityUserList.html" @click="onSelectMenu()">活动用户</menu-item>
							<menu-item name="1-3">Option 3</menu-item>
							<menu-item name="1-4">Option 4</menu-item>
						</submenu>
						<submenu name="2">
							<template slot="title">
								<icon type="ios-keypad"></icon>
								管理 2
							</template>
							<menu-item name="2-1">Option 1</menu-item>
							<menu-item name="2-2">Option 2</menu-item>
							<menu-item name="2-3">Option 3</menu-item>
							<menu-item name="2-4">Option 4</menu-item>
						</submenu>
						<submenu name="3">
							<template slot="title">
								<icon type="ios-analytics"></icon>
								管理 3
							</template>
							<menu-item name="3-1">Option 1</menu-item>
							<menu-item name="3-2">Option 2</menu-item>
						</submenu>
					</i-menu>
				</sider>
			</sider>
		</layout>

	</div>
</template>
<script type="text/javascript" src="../public/common.js"></script>
<script type="text/javascript" src="../layout/js/layout-sider.js"></script>