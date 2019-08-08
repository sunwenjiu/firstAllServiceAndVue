<template>
  <Row :gutter="16">
    <Modal
      v-model="warningModel"
      @on-ok="ok"
      width="400"
      @on-cancel="cancel">
      <p slot="header" class="warning-title"><span class="warning-title-ico"><Icon type="ios-alert" /></span>确认要提交报表么？</p>
      <p class="warning-text">注意:不良事件上报表一经提交，以后都不能修改，请确认所填写信息无误再提交！</p>
    </Modal>
    <Col span="18">
       <Card style="margin-bottom: 25px">
           <p slot="title">不良事件填报表</p>
           <div class="report-audit-add-form-box">
                <Form ref="formValidate" :model="formItemModel" :rules="ruleValidate" :label-width="95">
                      <Row :gutter="16">
                        <Col span="6">
                            <FormItem label="患者病案号:" prop="patientNumber">
                              <Input v-model="formItemModel.patientNumber" placeholder=""></Input>
                            </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="患者姓名:" prop="name">
                            <Input v-model="formItemModel.name" placeholder=""></Input>
                          </FormItem>
                        </Col>
                        <Col span="6">
                            <FormItem label="患者生日:"  prop="patientBirthday">
                              <DatePicker type="date" v-model="formItemModel.patientBirthday"placeholder=""></DatePicker>
                            </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="患者性别:"  prop="gender">
                            <RadioGroup v-model="formItemModel.gender">
                              <Radio label="MAN">男</Radio>
                              <Radio label="WOMAN">女</Radio>
                            </RadioGroup>
                          </FormItem>
                        </Col>
                      </Row>
                      <Row :gutter="16">
                        <Col span="6" class="spFormW">
                          <FormItem label="事件发生场所:" prop="eventAddressModel">
                            <Select v-model="formItemModel.eventAddressModel" filterable>
                              <Option v-for="item in eventAddress" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                          </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="患者科室:" prop="patientDeptModel">
                            <Select v-model="formItemModel.patientDeptModel" filterable>
                              <Option v-for="item in writeUserDept" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                           <!-- <AutoComplete
                              v-model="value3"
                              :data="data3"
                              :filter-method="filterMethod"
                              >
                            </AutoComplete>-->
                          </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="事件类型:" prop="eventTypeModel">
                                <Cascader :data="eventTypeData" v-model="formItemModel.eventTypeModel"></Cascader>
                          </FormItem>
                        </Col>
                      </Row>
                      <Row :gutter="16">
                        <Col span="6">
                          <FormItem label="填报人类型:" prop="writeUserTypeModel">
                            <Select v-model="formItemModel.writeUserTypeModel" filterable>
                              <Option v-for="item in writeUserType" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                          </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="填报人部门:"  prop="writeUserDeptModel">
                            <Select v-model="formItemModel.writeUserDeptModel" filterable>
                              <Option v-for="item in writeUserDept" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                          </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="填报人病区:"  prop="writeUserWardModel">
                            <Select v-model="formItemModel.writeUserWardModel" filterable>
                              <Option v-for="item in writeUserWard" :value="item.value" :key="item.value">{{ item.label }}</Option>
                            </Select>
                          </FormItem>
                        </Col>
                      </Row>
                      <div class="add-form-line"></div>
                      <Row :gutter="16">
                          <Col span="12">
                            <FormItem label="临床诊断:" prop="diagnosis">
                              <Input v-model="formItemModel.diagnosis" :rows="3" type="textarea" placeholder="临床诊断..." />
                            </FormItem>
                          </Col>
                          <Col span="12">
                            <FormItem label="事件描述:" prop="eventDesc">
                              <Input v-model="formItemModel.eventDesc" :rows="3" type="textarea" placeholder="事件描述..." />
                            </FormItem>
                          </Col>

                      </Row>
                      <Row :gutter="16">
                            <Col span="12">
                          <FormItem label="采取措施:" prop="measures">
                            <Input v-model="formItemModel.measures" :rows="3" type="textarea" placeholder="采取措施..." />
                          </FormItem>
                        </Col>
                            <Col span="12">
                              <FormItem label="处理情况:" prop="processing">
                                <Input v-model="formItemModel.processing" type="textarea" :rows="3" placeholder="事件处理情况..." />
                              </FormItem>
                            </Col>
                      </Row>
                      <div class="add-form-line"></div>

                     <!-- <FormItem
                        v-for="(item, index) in formItemModel.items"
                        v-if="item.status"
                        :key="index"
                        :label="'事件因素 ' + item.index"
                        :prop="'items.' + index + '.value'"
                        :rules="{required: true, message: 'Item ' + item.index +' can not be empty', trigger: 'change'}">

                        <Row>
                          <Col span="18">
                            <Row :gutter="16">
                              <Col span="8">
                                  <span style="float: left;margin-right: 10px">事件主因素:</span>
                                  <div style="float: left;width: 60%">
                                    <Select v-model="item.value" @on-change="eventReasonParentChange(item.value)">
                                      <Option v-for="itemMain in eventReasonParent" :value="itemMain.value" :key="itemMain.value">{{ itemMain.label }}</Option>
                                    </Select>
                                  </div>

                              </Col>

                              <Col span="16">
                                <span style="float: left;margin-right: 10px">事件子因素:</span>
                                <div style="float: left;width: 80%">

                                    <Select v-model="item.children" multiple @on-open-change="eventReasonChildrenChange(item.value,true)">
                                      <Option v-for="itemChil in eventReasonChildren" :value="itemChil.value" :key="itemChil.value">{{ itemChil.label }}</Option>
                                    </Select>

                                </div>
                              </Col>
                            </Row>
                          </Col>
                          <Col span="4" offset="1">
                            <Button @click="handleRemove(index)">删除</Button>
                          </Col>
                        </Row>
                      </FormItem>-->
                  <Row>
                    <Col span="7">
                      <FormItem
                        v-for="(item, index) in formItemModel.items"
                        v-if="item.status"
                        :key="index"
                        :label="'主原因 ' + item.index"
                        :prop="'items.' + index + '.value'"
                        :rules="{required: true, message: '主原因 ' + item.index +' 不能为空', trigger: 'change'}">
                        <!--<Row>
                          <Col span="18">-->
                          <!--  <Input type="text" v-model="item.value" placeholder="Enter something..."></Input>-->
                            <Select v-model="item.value" @on-change="eventReasonParentChange(item.value)">
                              <Option v-for="itemMain in eventReasonParent" :value="itemMain.value" :key="itemMain.value">{{ itemMain.label }}</Option>
                            </Select>
                         <!-- </Col>

                        </Row>-->
                      </FormItem>
                    </Col>
                    <Col span="15">
                      <FormItem
                        v-for="(item, index) in formItemModel.items"
                        v-if="item.status"
                        :key="index"
                        :label="'子原因 ' + item.index"
                        :prop="'items.' + index + '.children'"
                        :rules="{required: true,type: 'array', message: '子原因 ' + item.index +' 不能为空', trigger: 'change'}">
                        <Row>
                          <Col span="18">
                           <!-- <Input type="text" v-model="item.children" placeholder="Enter something..."></Input>-->

                            <Select v-model="item.children" multiple @on-open-change="eventReasonChildrenChange(item.value)" placeholder="可多选，先选择主原因"  not-found-text="请先选择主原因">
                              <Option v-for="itemChil in eventReasonChildren" :value="itemChil.value" :key="itemChil.value">{{ itemChil.label }}</Option>
                            </Select>
                          </Col>
                          <Col span="4" offset="1">
                            <Button @click="handleRemove(index)">Delete</Button>
                          </Col>
                        </Row>
                      </FormItem>
                    </Col>
                  </Row>
                      <FormItem>
                        <Row>
                          <Col span="12">
                            <Button type="dashed" long @click="handleAdd" icon="md-add">添加事件因素</Button>
                          </Col>
                        </Row>
                      </FormItem>
                      <div class="add-form-line"></div>
                      <Row :gutter="16">
                          <Col span="6">
                            <FormItem label="是否匿名:" prop="anon">
                              <RadioGroup v-model="formItemModel.anon">
                                <Radio label="y">是</Radio>
                                <Radio label="n">否</Radio>
                              </RadioGroup>
                            </FormItem>
                          </Col>
                        <Col span="6" >
                          <FormItem label="填报人姓名:" v-show="formItemModel.anon == 'n'">
                            <Input v-model="formItemModel.writeName" placeholder=""></Input>
                          </FormItem>
                        </Col>
                      </Row>
                      <FormItem>
                         <Button type="primary"  @click="upReportForm('formValidate')">提交报表</Button>
                         <Button type="primary" style="margin-left: 5px" @click="showData()">看数据的按钮要删除</Button>
                      </FormItem>
                </Form>
           </div>
       </Card>
    </Col>
    <Col span="6">
       <Card>
        <p slot="title">事件填报步骤</p>
         <Steps :current="0" direction="vertical">
           <Step title="进行中" content="创建表单并填报，填报完成后提交下步处理"></Step>
           <Step title="待处理" content="填报成功后，等待处理结果"></Step>
           <Step title="待结案" content="填报成功后，等待医务科结案处理"></Step>
           <Step title="已结案" content="不良事件结束"></Step>
         </Steps>
       </Card>
    </Col>
  </Row>

</template>

<script>
  import { mapActions } from 'vuex'
import { treeToRename, dictToRename, deptToRename, wardToRename,allToRename,treeTitleToRename } from './treeToRename'
export default {
  data () {
    return {
      warningModel: false,
      index: 1,
      model10: [],
      model15: [],
      ReasonTree: [],
            /* formDynamic: {
        items: [
          {
            value: '',
            index: 1,
            status: 1,
            children: []
          }
        ]
      }, */
      formItemModel: {
        patientNumber: '', // 病案号
        name: '',
        patientBirthday: '', // 生日
        gender: 'MAN', // 性别
        eventAddressModel: '', // 事件发生场所
        patientDeptModel: '', // 患者科室
        eventTypeModel: '', // 事件类型
        writeUserTypeModel: '', // 填报人类型
        writeUserDeptModel: '', // 填报人部门
        writeUserWardModel: '', // 填报人病区
        diagnosis: '', // 临床诊断
        eventDesc: '', // 事件描述
        measures: '', // 采取措施
        processing: '', // 事件处理情况
        reasonTreeId: [], // 事件主原因
        reasonSonTreeIds: [], // 事件子原因
        anon: 'y', // 是否匿名
        writeName: '', // 填报人姓名
        writeId: '', // 填报人姓名

        // 原因选择
        items: [
          {
            value: '',
            index: 1,
            status: 1,
            children: []
          }
        ],

        // 不知道的数据
        select: '',
        checkbox: [],
        switch: true,
        date: '',
        time: '',
        slider: [20, 50],
        textarea: ''
      },

      ruleValidate: {
        patientNumber: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],

        patientBirthday: [
          { required: true, type: 'date', message: '请选择生日', trigger: 'change' }
        ],
        gender: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        eventAddressModel: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        patientDeptModel: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        eventTypeModel: [
          { required: true,type: 'array', message: '请选择', trigger: 'change' }
        ],
        writeUserTypeModel: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        writeUserDeptModel: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        writeUserWardModel: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        diagnosis: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        eventDesc: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        measures: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        processing: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        anon: [
          { required: true, message: '请选择', trigger: 'change' }
        ]

      },
      // 事件发生场所
      eventAddress: [],
      // 填报人类型
      writeUserType: [],
      // 填报人部门
      writeUserDept: [],
      // 填报人病区
      writeUserWard: [],
      // 事件主因素
      eventReasonParent: [],
      // 事件子因素
      eventReasonChildren: [],
      // 事件类型
      eventTypeData: []
    }
  },
  mounted () {
    this.getEventTypeFromService() // 后台获取事件类型
    this.getdictDataFromService() // 获取字典库。事件发生场所和上报人类型
    this.getDeptDataFromService() // 向后台请求部门数据
    this.getwardDataFromService() // 向后台请求病区数据
    this.geteventReasonTreeDataFromService() // 向后台获取原因

    //封装用户
    this.getUserInfo().then( res =>{
      this.formItemModel.writeId =res.userId
      this.formItemModel.writeName =res.userName

    })
  },
  methods: {
    ...mapActions([
       'getUserInfo'
    ]),
    // 后台获取事件类型
    getEventTypeFromService () {
      this.axios.get('/eventType/show').then((response) => {
        const result = response.data.data
        const rename = treeTitleToRename(result)
        // console.log(rename)
        this.eventTypeData = rename
      }).catch((error) => {
        alert(error.message)
      })
    },
    // 获取字典库。事件发生场所和上报人类型
    getdictDataFromService () {
      this.axios.get('/dict/showAll').then((response) => {
        const result = response.data.data
        const renameEventPlacr = result.filter(p => p.dictType === 'EVENT_HAPPEN_PLACE')
        this.eventAddress = allToRename(renameEventPlacr)
        const renameEventP = result.filter(p => p.dictType === 'REPORTER')
        this.writeUserType = allToRename(renameEventP)
        // console.log(result)
      }).catch((error) => {
        alert(error.message)
      })
    },
    // 向后台请求部门数据
    getDeptDataFromService () {
      this.axios.get('/dept/showAll').then((response) => {
        const result = response.data.data
        this.writeUserDept = allToRename(result)
        // console.log(result)
      }).catch((error) => {
        alert(error.message)
      })
    },
    // 向后台请求病区数据
    getwardDataFromService () {
      this.axios.get('/ward/showAll').then((response) => {
        const result = response.data.data
        this.writeUserWard = allToRename(result)
        // console.log(this.writeUserWard)
      }).catch((error) => {
        alert(error.message)
      })
    },
    // 向后台获取原因
    geteventReasonTreeDataFromService () {
      this.axios.get('/eventReasonTree/show').then((response) => {
        const result = response.data.data
        // console.log(result)
        this.ReasonTree = treeToRename(result)
        this.eventReasonParent = this.ReasonTree
      }).catch((error) => {
        alert(error.message)
      })
    },

    // 选择主原因发生改变时，子原因的内容改变
    eventReasonParentChange (data) {
      // alert(data)
      const { ReasonTree } = this

      for (let i = 0; i < ReasonTree.length; i++) {
        if (ReasonTree[i].value === data) {
          this.eventReasonChildren = ReasonTree[i].children
        }
      }
    },
    // 当子原因选择器被打开始执行
    eventReasonChildrenChange (data) {
      if (data.length !== 32) {
        //alert('请先选择主原因')
        this.$Message.error('请先选择主原因!')
        this.eventReasonChildren = []
      } else {
        const { ReasonTree } = this

        for (let i = 0; i < ReasonTree.length; i++) {
          if (ReasonTree[i].value === data) {
            this.eventReasonChildren = ReasonTree[i].children
          }
        }
      }
    },

    //点击提交报表时
    upReportForm (name) {

      this.$refs[name].validate((valid) => {
        if (valid) {
          //友情提示，内容的重要性（model弹窗）
          this.warningModel = true
          //this.$Message.success('Success!')
        } else {
          this.$Message.error('请正确填写内容！')
        }
      })
    },
    ok () {
      //发生请求去创建
     /* 前端封装原因id格式为    主-子+子,主-子+子,主-子+子     */
      this.$refs.formValidate.validate(valid => {
        if (valid) {

          let strType = ''
          //现在用的传递最后一个的
          strType=this.formItemModel.eventTypeModel[this.formItemModel.eventTypeModel.length-1]

          //之前用的封装数组的
          //封装事件类型
          /*  const  TypeIindex = this.formItemModel.eventTypeModel.length
            for (let i=0;i<TypeIindex;i++) {
              if ( i === TypeIindex-1){
                //最后一个，不用加逗号

                strType += this.formItemModel.eventTypeModel[i]

              } else {
                strType += this.formItemModel.eventTypeModel[i]+ ','
              }
            }*/

          //封装原因节点
          /* 前端封装原因id格式为    主-子+子,主-子+子,主-子+子     */
        const items = this.formItemModel.items
          let itemsStr = ''
          for(let i = 0; i<items.length;i++){
          if(items[i].status ===0){

            continue
          }else {
            if(i ===items.length-1){
              //最后一个不用加，号
              itemsStr += items[i].value
              //进入子级
              const children = items[i].children
              for (let j = 0;j<items[i].children.length;j++){/*
                alert("进去子级循环")
                alert("items[i].children.length"+items[i].children.length)
                alert("j:"+j)*/
                if(j===0){
                  itemsStr += '-'+ children[0]
                  continue
                }else {
                  itemsStr += '+'+children[j]
                }
              }
              //子级结束
            }else {
              itemsStr += items[i].value
              //alert("进去父级中间了")
              //进入子级
              const children = items[i].children
              for (let j =0;j<children.length;j++){
                if(j===0){
                  itemsStr += '-'+ children[0]
                  continue
                }else {
                  itemsStr += '+'+children[j]
                }
              }
              //子级结束
              itemsStr += ','
            }
          }


          }
          //封装所有数据
          let createData ={
            /*患者姓名         */
            patientName:this.formItemModel.name,
            /*患者工号     */
            patientNumber:this.formItemModel.patientNumber,
            /*患者生日   */
            patientBirthday:this.formItemModel.patientBirthday,
            /*患者性别，枚举（0：男: MAN，1：女: WOMAN）   */
            patientSex:this.formItemModel.gender,
            /*患者科室/部门    */
            patientDept:this.formItemModel.patientDeptModel,
            /*患者临床诊断  */
            patientDiagnosis:this.formItemModel.diagnosis,
            /*事件描述 */
            eventDesc:this.formItemModel.eventDesc,
            /*事件发生场所   */
            eventAddress:this.formItemModel.eventAddressModel,
            /*事件类型  */
            eventType:strType,
            /*采取措施  */
            eventMeasures:this.formItemModel.measures,
            /*事件处理情况  */
            eventProcessing:this.formItemModel.processing,
            /*事件等级   */
            /*eventLevel:"aaa",*/
            /*填报人Id     */
            writeUserId: this.formItemModel.writeId,
            /*填报人姓名    */
            writeUserName:this.formItemModel.writeName,
            /*填报人类型      */
            writeUserType:this.formItemModel.writeUserTypeModel,
            /*填报人部门    */
            writeUserDept:this.formItemModel.writeUserDeptModel,
            /*填报人病区     */
            writeUserWard:this.formItemModel.writeUserWardModel,
            eventReasonSonIds:itemsStr,/*封装的事件原因*/
            isAnonymous: this.formItemModel.anon  /*是否匿名*/
          }

          this.axios.post('/event/create', createData ).then((response) => {
            const result = response.data
            const state = result.code
            if (state !== 200) {
              //this.userAddFormItem.nameAgin = result.msg

              ///this.$refs.userAddFormItem.validateField('name')
              /* 如果部门、科室名字重复，会执行下面代码 */
             /* if (this.userAddFormItem.nameAgin) {
                this.clearLoading()
              }*/
             // this.userAddFormItem.nameAgin = ''
            } else {
              //this.clearUserAddFormItem()
              //this.gradeTableModal = false

             // this.getDeptDataFromService()
            }
          }).catch((error) => {
            alert('请求失败' + error.message)
            this.clearLoading()
          })
        } else {
          // this.$Message.error('创建外失败A外')
          this.clearLoading()
        }
      })
      this.$Message.info('Clicked ok')
    },
    cancel () {
      this.$Message.info('Clicked cancel')
    },
    // 添加事件原因
    handleAdd () {
      this.index++
      this.formItemModel.items.push({
        value: '',
        index: this.index,
        status: 1,
        children: []
      })
    },
    handleRemove (index) {
      // this.index--;
      // this.formDynamic.items.splice(index,1);

      this.formItemModel.items[index].status = 0
    },
    /* filterMethod (value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    }, */
    showData () {
      // console.log("事件类型数据结构："+this.formItemModel.eventTypeModel)
      console.log(this.formItemModel)
     // console.log(this.formItemModel.items)
      // console.log(this.ReasonTree)
      /*this.getUserInfo().then(res => {
        this.$router.push({
          name: this.$config.homeName
        })
      })*/

      console.log( this.formItemModel.writeId)
      alert(this.formItemModel.writeName)
     // alert(this.getUserInfo().PromiseValue.userId)
    }
  }
}
</script>

<style scoped>
  .warning-text{
    font-size: 12px;
    font-family: 微软雅黑;
  }
  .warning-title-ico{
    font-size: 24px;
    color: #ff9900;
    margin-right: 8px;
    /*vertical-align: middle;*/
  }
  .warning-title{
    font-size: 18px;
    font-family: 微软雅黑;
    font-weight: bold;
    color: #323232;
    padding-bottom: 5px;
  }
  .add-form-line{
    margin-bottom: 20px;
    height: 2px;
    width: 100%;
    background-color: #808695;
  }
        .spFormW .ivu-form-item-content{
            margin-left: 100px;
        }
        .spFormW .ivu-form-item-label{
            width: 100px !important;
          height: 100px;
        }
       .addFormWidth{
           width: 33%;
       }
</style>
