<template>
  <div>
    <!--用户添加-->
    <Modal
      v-model="gradeTableModal"
      title="新用户添加"
      @on-ok="ok"
      width="400"
      @on-cancel="cancel">
      <div>
        <Form :model="userAddFormItem" :label-width="80">
          <FormItem label="名字">
            <Input v-model="userAddFormItem.input" style="width: 250px" placeholder="请输入用户信息"></Input>
          </FormItem>
          <FormItem label="性别">
            <RadioGroup v-model="userAddFormItem.radio">
              <Radio label="male">男</Radio>
              <Radio label="female">女</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="联系方式">
            <Input v-model="userAddFormItem.input" style="width: 250px" placeholder="请输入用户手机号"></Input>
          </FormItem>
          <FormItem label="身份证">
            <Input v-model="userAddFormItem.input" style="width: 250px" placeholder="请输入用户信息"></Input>
          </FormItem>
          <FormItem label="职称">
            <Select v-model="userAddFormItem.select" style="width: 250px">
              <Option value="beijing">主任</Option>
              <Option value="shanghai">副主任</Option>
            </Select>
          </FormItem>
          <FormItem label="职位">
            <Select v-model="userAddFormItem.position" style="width: 250px">
              <Option value="beijing">主任</Option>
              <Option value="shanghai">副主任</Option>
            </Select>
          </FormItem>
          <FormItem label="类型">
            <Select v-model="userAddFormItem.duty" style="width: 250px">
              <Option value="beijing">New York</Option>
              <Option value="shanghai">London</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
    </Modal>
    <Card shadow title="不良事件处理方式">
      <div class="userTable-title-btn">
        <Button type="primary" icon="md-person-add" @click="addClick">添加</Button>
      </div>
      <div>
        <Table :columns="userTable" :data="userData"></Table>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: 'adverse-dispose',
  data () {
    return {
      gradeTableModal: false,
      userAddFormItem: {
        duty: '',
        input: '',
        select: '',
        position: '',
        radio: 'male',
        time: ''
      },
      userTable: [
        {
          title: '姓名',
          key: 'name'
        },
        {
          title: '年龄',
          key: 'age'
        },
        {
          title: '地址',
          key: 'address'
        }
      ],
      userData: [
        {
          name: '张三',
          age: 18,
          address: '马街404号',
          date: '2019-06-24'
        },
        {
          name: '李四',
          age: 18,
          address: '马街404号',
          date: '2019-06-24'
        },
        {
          name: '赵五',
          age: 18,
          address: '马街404号',
          date: '2019-06-24'
        }
      ]
    }
  },
  methods: {
    ok () {
      this.$Notice.success({
        title: '用户创建成功'
      })
    },
    cancel () {
      this.$Message.info('取消了创建用户')
    },
    addClick () {
      this.gradeTableModal = true
    }
  }
}
</script>

<style scoped>
  .userTable-title-btn{
    margin-bottom: 20px;
  }
</style>
