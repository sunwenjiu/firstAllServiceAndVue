<template>
    <div class="StudyIndexAlterBox">
        <div class="StudyIndexBox">
            <!--主菜单列表-->
            <Row>
                <Col :md="24">
                    <div class="ModifyTable">
                        <div class="ModifyTableOperation" v-if="1 == 1">
                            <a class="ModifyTableBtnStyle"  @click="addResource"><Icon type="plus-round" style="margin-right: 5px; font-size: 13px"></Icon>添加功能</a>
                            <!--<a class="ModifyTableBtnStyle1"  @click="deleteResource"><Icon type="trash-b" style="margin-right: 5px; font-size: 13px"></Icon>批量删除</a>-->
                        </div>
                        <Table :columns="resourceColumns" :data="resourceData" border @on-selection-change="selectionChange" @on-sort-change="sortChange"/>
                        <div class="PagingBox">
                            <Page :total="1" size="small" show-elevator></Page>
                        </div>
                    </div>
                </Col>
            </Row>
            <!--添加或编辑主菜单模态-->
            <Modal v-model="resourceModal"
                   :title="modalTitle"
                   :mask-closable="false"
                   class-name="SmModaStyle"
            >
                <Form ref="resourceObject" :model="resourceObject" :rules="resourceValidate" :label-width="100">
                    <FormItem prop="resName" label="功能名称">
                        <Input v-model="resourceObject.dataSourceName" placeholder="请输入功能名称" style="width: 220px;"></Input>
                    </FormItem>
                    <FormItem prop="resCode" label="功能描述">
                        <Input v-model="resourceObject.dataSourceName" placeholder="请输入功能描述" style="width: 220px;"></Input>
                    </FormItem>
                    <FormItem label="激活状态">
                        <RadioGroup v-model="resourceObject.available">
                            <Radio label="0">启用</Radio>
                            <Radio label="1">禁用</Radio>
                        </RadioGroup>
                    </FormItem>
                </Form>
                <div slot="footer">
                    <Button type="ghost" @click="cancel">
                        <Icon type="close"/>
                        取消
                    </Button>
                    <Button type="primary" @click="addEditResource">
                        <Icon type="checkmark"></Icon>
                        提交
                    </Button>
                </div>
            </Modal>
            <!--图标选择模态-->
            <Modal v-model="iconChangeModal"
                   title="选择图标"
                   :mask-closable="false"
                   class-name="SmModaStyle2"
            >
                <div style="overflow: hidden">
                    <div v-if="iconData.length === 0">
                        <span>无图标数据，请联系管理员！</span>
                    </div>
                    <div v-else v-for="icon in iconData" style="float: left">
                        <Button type="ghost" @click="iconChangeOk(icon.code)" style="margin: 5px; width: 50px; height: 40px">
                            <Icon :type=icon.code style="font-size: 20px"></Icon>
                        </Button>
                    </div>
                </div>
                <div slot="footer">
                    <div class="PagingBox2">
                        <Page :total="totalPages" @on-change="iconList" size="small" show-elevator></Page>
                    </div>
                </div>
            </Modal>
            <!--菜单子级管理模态-->
            <Modal v-model="viewResourceModal"
                   :title="modalTitle"
                   :mask-closable="false"
                   class-name="vertical-center-modal AddStyle "
                   width="1000">
                <Row>
                    <Col span="6">
                        <div>
                            <Button class="BtnStyle" type="primary"
                                    @click="addChildren">
                                <Icon type="plus"/>
                                添加
                            </Button>
                            <Button class="BtnStyle" type="primary"
                                    @click="editChildren">
                                <Icon type="edit"/>
                                编辑
                            </Button>
                            <Button class="BtnStyle" type="error"
                                    @click="delChildren">
                                <Icon type="trash-b"/>
                                删除
                            </Button>
                        </div>
                        <div class="TreeBox" style="margin-top: 18px;">
                            <div class="TreeBoxTitle">
                                <p class="TreeTitle2">
                                    <Icon type="ios-list-outline"></Icon>
                                    子功能列表
                                </p>
                            </div>
                            <Tree :data="treeData" @on-select-change="selectNode"></Tree>
                        </div>
                    </Col>
                    <Col span="18">
                        <div>
                            <Button class="BtnStyle" type="primary"
                                    @click="addPermission">
                                <Icon type="plus"/>
                                添加权限
                            </Button>
                        </div>
                        <br/>
                        <Table :columns="permissionColumns" :data="permissionData"></Table>
                    </Col>
                </Row>
                <div slot="footer">
                    <Button type="ghost" @click="viewResourceModal=false ">
                        <Icon type="close"/>
                        关闭
                    </Button>
                </div>
            </Modal>
            <!--添加或编辑子菜单模态-->
            <Modal v-model="childrenModal"
                   title="添加子功能"
                   :mask-closable="false"
                   class-name="SmModaStyle">
                <Form ref="childrenObject" :model="childrenObject" :rules="childrenValidate" :label-width="80 ">
                    <FormItem label="功能名称 " prop="resName">
                        <Input v-model="childrenObject.dataSourceName" placeholder="请输入功能名称 " style="width: 200px;"></Input>
                    </FormItem>
                    <FormItem label="功能描述 " prop="resCode">
                        <Input v-model="childrenObject.description" placeholder="请输入功能编码 " style="width: 200px;"></Input>
                    </FormItem>
                    <FormItem label="上级功能 ">
                        <Input v-model="childrenObject.parentName" readonly style="width: 200px;"></Input>
                    </FormItem>
                    <FormItem>
                        <input v-model="childrenObject.partentId" type="hidden"/>
                    </FormItem>
                </Form>
                <div slot="footer">
                    <Button type="ghost" @click="cancel">
                        <Icon type="close "/>
                        取消
                    </Button>
                    <Button type="primary" @click="addEditChildren">
                        <Icon type="checkmark"></Icon>
                        提交
                    </Button>
                </div>
            </Modal>
            <!--添加或编辑权限模态-->
            <Modal v-model="permissionModal"
                   :title="modalTitle"
                   :mask-closable="false"
                   class-name="SmModaStyle">
                <Form ref="permissionObject" :model="permissionObject" :rules="permissionValidate" :label-width="80">
                    <FormItem label="权限编码 " prop="perCode">
                        <Input v-model="permissionObject.permission" placeholder="请输入编码 " style="width: 200px;"></Input>
                    </FormItem>
                    <FormItem label="权限名称 " prop="perName">
                        <Input v-model="permissionObject.description" placeholder="请输入名称 " style="width: 200px;"></Input>
                    </FormItem>
                    <FormItem label="权限说明 ">
                        <Input type="textarea" v-model="permissionObject.description" placeholder="权限说明.... "
                               style="width: 200px;"></Input>
                    </FormItem>
                    <FormItem>
                        <input type="hidden" v-model="permissionObject.groupId"/>
                    </FormItem>
                </Form>
                <div slot="footer">
                    <Button type="ghost" @click="cancel">
                        <Icon type="close"/>
                        取消
                    </Button>
                    <Button type="primary" @click="addEditPermission">
                        <Icon type="checkmark"></Icon>
                        提交
                    </Button>
                </div>
            </Modal>
            <!--删除菜单模态框-->
            <Modal v-model="delResourceModal"
                    title="删除功能"
                    :mask-closable="false"
                    @on-ok="deleteResourceSubmit()"
                    class="SmModaStyle"
                    @on-cancel="delResourceModal = false">
                <p class="SmModaTextStyle">
                    <Icon type="help-circled"></Icon>
                    确定要删除功能【{{resourceObject.dataSourceName}}】么？
                </p>
            </Modal>
            <!--删除子菜单模态框-->
            <Modal v-model="delChildrenModal"
                    title="删除子功能"
                    :mask-closable="false"
                    @on-ok="delChildrenSubmit"
                    class="SmModaStyle"
                    @on-cancel="delChildrenModal = false">
                <p class="SmModaTextStyle">
                    <Icon type="help-circled"></Icon>
                    确定要删除子功能【{{names}}】么？
                </p>
            </Modal>
            <!--删除权限模态框-->
            <Modal v-model="delPermissionModal"
                    title="删除权限"
                    :mask-closable="false"
                    @on-ok="delPermissionSubmit"
                    class="SmModaStyle"
                    @on-cancel="delPermissionModal = false">
                <p class="SmModaTextStyle">
                    <Icon type="help-circled"></Icon>
                    确定要删除权限【{{names}}】么？
                </p>
            </Modal>
        </div>
    </div>
</template>
<script type="text/javascript" src="./Resource.js"></script>
<style>
    .icoBoxStyle{
        overflow: hidden;
    }
    .TreeTitle2{
        color: #FFFFFF;
        margin-left: 10px;
        padding-top: 6px;
    }
    .SmModaStyle{
    }
    .SmModaStyle .ivu-modal {
        z-index: 999999999 !important;
        width: 400px !important;
    }
    .SmModaStyle2 {
        z-index: 9999999999999 !important;
    }
    .SmModaStyle2 .ivu-modal {
        width: 400px;
    }
    .BtnStyle {
        padding: 3px 12px !important;
        border-radius: 0;
        margin-right: 10px;
    }

    .WidthStyle .ivu-modal-body {
        padding-top: 4px !important;
    }

    .TreeTitle {
        color: #FFFFFF;
        margin-left: 10px;
        padding-top: 6px;
    }

    .TreeBoxTitle {
        width: 100%;
        height: 30px;
        background-color: #5cadff;
    }

    .TreeBox .ivu-tree-children li {
        margin-left: 10px;
    }

    .TreeBox {
        border: solid 1px #5cadff;
        margin-right: 20px;
    }

    .AddStyle .ivu-modal-close i {
        color: #FFFFFF;
    }

    .AddStyle .ivu-modal-header .ivu-modal-header-inner {
        color: #FFFFFF !important;
        font-size: 14px;
        font-weight: 800;
        font-family: "微软雅黑";
    }

    .AddStyle .ivu-modal-header {
        background-color: #5cadff;
        border-radius: 7px 7px 0 0;
        margin-bottom: 10px;
        border-bottom: solid 1px;
        padding-top: 10px !important;
        padding-bottom: 10px !important;
    }
    .PagingBox{  text-align: center;margin-top: 30px; margin-bottom: 20px;  }
    .PagingBox2{  text-align: center;margin-top: 10px; margin-bottom: 10px;  }
    .StudyFromStyle .ivu-form-item-content{ margin-left: 70px !important; }
    .StudyFromStyle .ivu-form-item-label{ width: 70px !important; }
    .StudyFromStyle{ margin-left: 30px; }
    .WrodAddBox .ivu-form-item-content{ margin-left: 0 !important;  }
    .ModifyTableBtnStyle i{ margin-right: 5px; font-size: 16px;vertical-align: middle; }
    .ModifyTableOperation{ overflow: hidden;margin-bottom: 15px; }
    .ModifyTableBtnStyle:hover{ background-color: #2992ef;  color: #ffffff;}
    .ModifyTableBtnStyle1:hover{ background-color: #be1428;  color: #ffffff;}
    .ModifyTableBtnStyle{
        background-color: #57a3f3;
        display: inline-block;
        float: left;
        width: 100px;
        height: 28px;
        text-align: center;
        color: #ffffff;
        font-size: 12px;
        line-height: 26px;
        margin-right: 10px;
    }
    .ModifyTableBtnStyle1{
        background-color: #ed3f14;
        display: inline-block;
        float: left;
        width: 100px;
        height: 28px;
        text-align: center;
        color: #ffffff;
        font-size: 12px;
        line-height: 26px;
    }
    /*.ModifyTable{*/
        /*margin: 15px;*/
    /*}*/
    .SmModaTextStyle i {
        margin-right: 5px;
        color: #ff9900;
    }

    .SmModaTextStyle {
        font-size: 16px;
        margin-bottom: 20px;
    }

    .StudyAlterTitle h1 {
        font-family: "微软雅黑";
        font-size: 14px;
        color: #ffffff;
        padding: 5px;
        font-weight: 200;
        margin-left: 10px;
    }

    .StudyDeleteWarning span {
        font-size: 28px;
        vertical-align: middle;
        margin-right: 15px;
        color: #ff9900;
    }

    .StudyDeleteWarning {
        text-align: center;
        font-size: 18px;
    }

    .StudyWidthStyle .ivu-modal {
        width: 600px !important;
    }

    a {
        moz-user-select: -moz-none;
        -moz-user-select: none;
        -o-user-select: none;
        -khtml-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }

    .demo-upload-list {
        display: inline-block;
        width: 60px;
        height: 60px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
        margin-right: 4px;
    }

    .demo-upload-list img {
        width: 100%;
        height: 100%;
    }

    .demo-upload-list-cover {
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0, 0, 0, .6);
    }

    .demo-upload-list:hover .demo-upload-list-cover {
        display: block;
    }

    .demo-upload-list-cover i {
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        margin: 0 2px;
    }

    .StudyTreeBox .ivu-tree {
        margin: 3%;
    }

    .StudyTreeBox .ivu-tree-title {
        padding: 3px 5px;
    }

    .StudyTreeBox .ivu-tree .ivu-tree-children {
    }
    .TreeTitle {

        margin-left: 20px;
        font-weight: 900;
        font-family: "微软雅黑";
    }

    .StudyTreeTitleBox p {
        color: #ffffff;
    }

</style>
