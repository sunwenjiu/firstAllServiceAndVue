<template>
    <div class="StudyIndexAlterBox">
        <div class="StudyIndexBox">
            <!--添加用户模态框-->
            <Modal
                    v-model="addUserModal"
                    :title="modalTitle"
                    :mask-closable="false"
                    @on-ok="addUser"
                    @on-cancel="cancel"
                    class="SmModaStyle">

                <div class="StudyFromStyle">
                    <Form ref="UserObject" :model="UserObject" :rules="UserRules" label-position="left" :label-width="100">
                        <FormItem prop="userName" label="登录名">
                            <div v-if="status == 'save'">
                                <Input v-model="UserObject.username"  placeholder="登录名不可修改"  style="width: 220px;"></Input>
                            </div>
                            <div v-if="status == 'edit'">
                                <Input v-model="UserObject.username"  placeholder="请输入登录名" readonly="readonly" style="width: 220px;"></Input>
                            </div>
                        </FormItem>
                        <div v-if="status == 'save'">
                            <FormItem prop="userPassword" label="密码">
                                <Input v-model="UserObject.userpassword" placeholder="请输入密码" type="password" style="width: 220px;"></Input>
                            </FormItem>
                        </div>
                        <FormItem label="用户类型">
                            <RadioGroup v-model="UserObject.type">
                                <Radio label="MAN">医生</Radio>
                                <Radio label="WOMAN">护士</Radio>
                                <Radio label="UNKNOW">行政</Radio>
                            </RadioGroup>
                        </FormItem>
                        <FormItem prop="userTel" label="电话">
                            <Input v-model="UserObject.phoneNumber" placeholder="请输入手机号" style="width: 220px;"></Input>
                        </FormItem>
                        <FormItem label="启用/禁用">
                            <RadioGroup v-model="UserObject.avaliable">
                                <Radio label="1">启用</Radio>
                                <Radio label="0">禁用</Radio>
                            </RadioGroup>
                        </FormItem>
                    </Form>
                </div>
                <div slot="footer">
                    <Button type="ghost" @click="cancel">
                        <Icon type="close"/>
                        取消
                    </Button>
                    <Button type="primary" @click="addUser">
                        <Icon type="checkmark"></Icon>
                        提交
                    </Button>
                </div>
            </Modal>
            <!--删除用户模态框-->
            <Modal
                    v-model="delUserModal"
                    title="删除用户"
                    :mask-closable="false"
                    @on-ok="delUser()"
                    class="SmModaStyle"
                    @on-cancel="cancel()">
                <p class="SmModaTextStyle">
                    <Icon type="help-circled"></Icon>
                    确定要删除用户【{{UserObject.username}}】么？
                </p>
            </Modal>
            <!--重置用户密码模态框-->
            <Modal
                    v-model="rePasswordModal"
                    title="重置密码"
                    :mask-closable="false"
                    @on-ok="addUser()"
                    class="SmModaStyle"
                    @on-cancel="cancel()">
                <p class="SmModaTextStyle">
                    <Icon type="help-circled"></Icon>
                    确定要把用户【{{UserObject.userName}}】的密码重置为“888888”么？
                </p>
            </Modal>
            <!--分配角色模态框-->
            <Modal v-model="assignRoleModal" class-name="AddStyle" width="720">
                <p slot="header" style="color:#000000;text-align:left; text-shadow: 0 0 5px rgba(0,0,0,0.3);">
                    <Icon type="person-stalker"></Icon>
                    <span>分配角色</span>
                </p>
                <Transfer :data="roleData" :titles="['可选角色','已选角色']" :operations="['移除','添加']" :target-keys="userRole" :list-style="listStyle" :render-format="renderFormat" filterable @on-change="handleChange">
                </Transfer>
                <div slot="footer">
                    <Button type="ghost" @click="closeRoleeModel"><Icon type="close"/>关闭</Button>
                </div>
            </Modal>
            <!--页面主体-->
            <Row>
                <Col :md="24">
                    <div class="ModifyTable">
                        <div class="ModifyTableOperation" v-if="1 == 1">
                            <a class="ModifyTableBtnStyle"  @click="addUserButton"><Icon type="plus-round"></Icon>添加用户</a>
                        </div>
                       <Table border :columns="columnName" :data="UserData"></Table>
                        <div class="PagingBox">
                            <Page :total="totalPages" @on-change="UserList" size="small" show-elevator></Page>
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
    </div>
</template>
<style>
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
    .AddStyle .ivu-modal-header i{
        color: #ffffff;
    }
    .AddStyle .ivu-modal-header span{
        color: #ffffff;
    }
</style>
<script type="text/javascript" src="./User.js"></script>
