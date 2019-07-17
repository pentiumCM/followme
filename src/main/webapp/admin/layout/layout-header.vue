<template>
  <div class="layout" id="layout-header">
    <layout>
      <i-header>
        <i-menu mode="horizontal" theme="dark" active-name="1">
          <div style="float:left">
            <font
              style="font-size: 20pt; filter: shadow(color = #9370db); width: 30%; color: #E0FFFF; line-height: 150%; font-family: 隶书"
            >Followme后台管理系统</font>
          </div>
          <div style="float:right">
            <font style="font-size: 20pt; color: #E0FFFF;font-family: 隶书">欢迎您：</font>
            <Dropdown trigger="click" transfer>
              <a href="javascript:void(0)">
               <font style="font-size: 20pt; color: #E0FFFF;font-family: 隶书">{{loginName}}</font>
              </a>
              <Dropdown-menu slot="list">
                <Dropdown-item>信息</Dropdown-item>
                <a href="#" @click="adminLogOut()"><Dropdown-item>退出</Dropdown-item></a>
              </Dropdown-menu>
            </Dropdown>
          </div>
        </i-menu>
      </i-header>
    </layout>
  </div>
</template>
<script type="text/javascript" src="../public/common.js"></script>
<script type="text/javascript" src="./layout/js/layout-header.js"></script>