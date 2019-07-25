<template>
    <div class="StudyIndexAlterBox">
        <div class="StudyIndexBox">
            <!--添加角色模态框-->
            <Modal
                    v-model="addRoleModal"
                    :title="modalTitle"
                    :mask-closable="false"
                    @on-ok="addRole"
                    @on-cancel="cancel"
                    class="SmModaStyle">

                <div class="StudyFromStyle">
                    <Form ref="roleObject" :model="roleObject" :rules="roleRules" label-position="left" :label-width="100">
                        <FormItem prop="roleName" label="角色名">
                            <Input v-model="roleObject.rolename" placeholder="请输入角色名称" style="width: 220px;"></Input>
                        </FormItem>
                        <FormItem prop="roleCode" label="状态">
                            <RadioGroup v-model="roleObject.available">
                                <Radio label="0">启用</Radio>
                                <Radio label="1">禁用</Radio>
                            </RadioGroup>
                        </FormItem>
                        <FormItem label="角色描述">
                            <Input v-model="roleObject.description" placeholder="请输入角色描述" style="width: 220px;"></Input>
                        </FormItem>
                    </Form>
                </div>
                <div slot="footer">
                    <Button type="ghost" @click="cancel">
                        <Icon type="close"/>
                        取消
                    </Button>
                    <Button type="primary" @click="addRole">
                        <Icon type="checkmark"></Icon>
                        提交
                    </Button>
                </div>
            </Modal>
            <!--删除角色模态框-->
            <Modal
                    v-model="delRoleModal"
                    title="删除角色"
                    :mask-closable="false"
                    @on-ok="delRole"
                    class="SmModaStyle"
                    @on-cancel="cancel()">
                <p class="SmModaTextStyle">
                    <Icon type="help-circled"></Icon>
                    确定要删除角色【{{roleObject.rolename}}】么？
                </p>
            </Modal>
            <!--权限分配模态框-->
            <Modal v-model="permissionModal"
                   :mask-closable="false"
                   class-name="vertical-center-modal AddStyle" width="800">
                <p slot="header" style="color:#ffffff;text-align:left; text-shadow: 0 0 5px rgba(0,0,0,0.3);">
                    <Icon type="settings"></Icon>
                    <span>权限分配</span>
                </p>
                <Row>
                    <Col span="6">
                        <div class="TreeBox">
                            <div class="TreeBoxTitle">
                                <p class="TreeTitle2">
                                    <Icon type="social-windows"></Icon>
                                    系统平台</p>
                            </div>
                            <Tree :data="treeData" @on-select-change="selectNode"></Tree>
                        </div>
                    </Col>
                    <Col span="18">
                        <Table :columns="permissionColumns" :data="permissionData"></Table>
                    </Col>
                </Row>
                <div slot="footer">
                    <Button type="ghost" @click="permissionModal = false"><Icon type="close"/>关闭</Button>
                </div>
            </Modal>
            <!--页面主体-->
            <Row>
                <Col :md="24">
                    <div class="ModifyTable">
                        <div class="ModifyTableOperation" v-if="1 == 1">
                            <a class="ModifyTableBtnStyle"  @click="addRoleButton"><Icon type="plus-round"></Icon>添加角色</a>
                        </div>
                        <Table border :columns="columnName" :data="roleData"></Table>
                        <div class="PagingBox">
                            <Page :total="totalPages" @on-change="pageChange" size="small" show-elevator></Page>
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
    </div>
</template>
<style>
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
    .TreeTitle2{
        color: #FFFFFF;
        margin-left: 10px;
        padding-top: 6px;
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
    .PagingBox{  text-align: center;margin-top: 30px; margin-bottom: 20px;  }
    .StudyFromStyle .ivu-form-item-content{ margin-left: 70px !important; }
    .StudyFromStyle .ivu-form-item-label{ width: 70px !important; }
    .StudyFromStyle{ margin-left: 30px; }
    .WrodAddBox .ivu-form-item-content{ margin-left: 0 !important;  }
    .ModifyTableBtnStyle i{ margin-right: 5px; font-size: 16px;vertical-align: middle; }
    .ModifyTableOperation{ overflow: hidden;margin-bottom: 15px; }
    .ModifyTableBtnStyle:hover{ background-color: #2992ef;  color: #ffffff;}
    .ModifyTableBtnStyle{
        background-color: #57a3f3;
        display: block;
        width: 100px;
        height: 28px;
        text-align: center;
        color: #ffffff;
        font-size: 12px;
        line-height: 26px;
    }
    .ModifyTable{
        /*margin: 15px;*/
    }
    .SmModaTextStyle i {
        margin-right: 5px;
        color: #ff9900;
    }

    .SmModaTextStyle {
        font-size: 16px;
        margin-bottom: 20px;
    }

    .SmModaStyle .ivu-modal {
        width: 400px !important;
    }

    /*.StudyFrom {*/
        /*margin-top: 33px;*/
    /*}*/

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

    .StudyTreeTitleBox p {
        color: #ffffff;
    }

</style>
<script type="text/javascript" src="./Role.js"></script>
