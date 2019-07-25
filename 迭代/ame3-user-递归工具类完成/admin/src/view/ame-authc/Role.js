import * as $ from 'jquery'

export default {
  data () {
    return {
      flag: true,
      // 功能列表
      columnName: [
        {
          title: '序号',
          key: 'roleId',
          width: 80,
          render: (h, params) => {
            return h('div', [
              h('strong', params.index + 1)
            ])
          }
        },
        {
          title: '角色名',
          key: 'rolename'
        },
        {
          title: '激活状态',
          key: 'available'
        },
        {
          title: '描述',
          key: 'description'
        },
        {
          title: '创建时间',
          key: 'createTime',
          render: (h, params) => {
            return h('p', this.xdate.format(params.row.createTime, 'yyyy-MM-dd HH:mm:ss'))
          },
          sortable: true
        },
        {
          title: '修改时间',
          key: 'updateTime',
          render: (h, params) => {
            return h('p', this.xdate.format(params.row.updateTime, 'yyyy-MM-dd HH:mm:ss'))
          },
          sortable: true
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 280,
          render: (h, params) => {
            let actions = []
            if (true) {
              actions.push(h('Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  icon: 'edit'
                },
                style: {
                  marginRight: '10px'
                },
                on: {
                  click: () => {
                    this.flag = true
                    this.roleObject = $.extend(true, {}, params.row)
                    this.addRoleModal = true
                    this.modalTitle = '编辑角色'
                  }
                }
              }, '编辑'))
            }
            if (true) {
              actions.push(h('Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  icon: 'wrench'
                },
                style: {
                  marginRight: '10px'
                },
                on: {
                  click: () => {
                    this.flag = true
                    this.roleObject = $.extend(true, {}, params.row)
                    this.modalTitle = '编辑角色'
                    this.assignPermission()
                  }
                }
              }, '分配权限'))
            }
            if (true) {
              actions.push(h('Button', {
                props: {
                  type: 'error',
                  size: 'small',
                  icon: 'trash-b'
                },
                on: {
                  click: () => {
                    this.roleObject = $.extend(true, {}, params.row)
                    this.delRoleModal = true
                  }
                }
              }, '删除'))
            }
            return h('div', actions)
          }
        }
      ],
      // 权限列表
      permissionColumns: [
        {
          title: '序号',
          type: 'index',
          width: 60,
          align: 'center',
          key: 'permissionId'
        },
        {
          title: '权限名称',
          key: 'description'
        },
        {
          title: '权限编码',
          key: 'permission'
        }, {
          title: '状态',
          width: 50,
          fixed: 'right',
          align: 'center',
          render: (h, params) => {
            return h('div', [h('Checkbox', {
              props: {
                // value: params.row.available
                value: params.row.available == '1'
              },
              on: {
                'on-change': () => {
                  params.row.available = event.target.checked
                  this.changePermission(params.row)
                }
              }
            })])
          }
        }
      ],
      roleObject: {
        roleId: '',
        rolename: '',
        available: '',
        description: ''
      },
      roleData: [
        { roleName: 'admin', roleCode: '启用', roleDescription: '1321321321321', createTime: '12165', updateTime: '136156' },
        { roleName: 'admin', roleCode: '启用', roleDescription: '1321321321321', createTime: '12165', updateTime: '136156' },
        { roleName: 'admin', roleCode: '启用', roleDescription: '1321321321321', createTime: '12165', updateTime: '136156' }
      ], // 角色数据
      permissionData: [], // 权限数据
      treeData: [], // 功能列表树
      pageNum: 0, // 起始页
      pageSize: 10, // 每页显示的条目数
      totalPages: 10, // 列表总页数，默认1页
      addRoleModal: false,
      delRoleModal: false,
      permissionModal: false,
      modalTitle: '',
      roleRules: {
        roleName: [{
          required: true,
          message: '请填写角色名称',
          trigger: 'blur'
        }],
        roleCode: [{
          required: true,
          message: '请填写Code',
          trigger: 'blur'
        }]
      }
    }
  },
  mounted () {
    this.roleList(this.pageNum)
  },
  methods: {
    ok () {
    },
    cancel () {
      this.addRoleModal = false
      this.$refs['roleObject'].resetFields()
    },
    // 角色列表，打开页面自动加载
    roleList (pageNum) {
      let requestParam = {
        pageNumber: pageNum,
        pageSize: 10
      }
      this.axios.get('authority/getAllRoleByPage', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.roleData = res.data.data.content
          this.totalPages = res.data.data.totalPages
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    // 页码切换
    pageChange (page) {
      let pageNumber = page - 1
      this.roleList(pageNumber)
    },
    // 添加角色按钮
    addRoleButton () {
      this.addRoleModal = true
      this.flag = true
      this.modalTitle = '添加角色'
      this.roleObject = {
        roleId: '',
        rolename: '',
        available: '0',
        description: ''
      }
    },
    // 添加编辑角色
    addRole () {
      let role = {
        roleId: this.roleObject.roleId,
        rolename: this.roleObject.rolename,
        description: this.roleObject.description,
        available: this.roleObject.available
      }
      this.axios.post('authority/saveOrUpdateRole', role).then((res) => {
        if (res.data.code == 0) {
          this.roleList(this.pageNum)
        }
      }).catch((error) => {
        console.log(error)
      })
      this.addRoleModal = false
    },
    // 获取资源树
    assignPermission () {
      this.permissionData = []
      this.axios.get('authority/getDataSourcePnByTree').then((res) => {
        let root = {
          id: '0',
          title: '功能列表',
          expand: true
        }
        if (res.data.code === 0) {
          root.children = res.data.data
          this.treeData = []
          this.treeData.push(root)
        }
      }).catch((error) => {
        console.log(error)
      })
      this.permissionModal = true
    },
    // 删除角色
    delRole () {
      let requestParam = {
        roleId: this.roleObject.roleId
      }
      this.axios.get('authority/deleteRole', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.roleList(this.pageNum)
        }
      }).catch((error) => {
        console.log(error)
      })
      this.delRoleModal = false
    },
    // 选择资源树后加载权限
    selectNode (selectNodes) {
      if (selectNodes.length < 1) {
        this.permissionData = []
        return false
      }
      this.selectNodes = selectNodes
      let requestParams = {
        psdId: this.selectNodes[0].dataSourcePnId,
        roldId: this.roleObject.roleId
      }
      this.axios.get('authority/getPermissionByDataSourceTree', { params: requestParams }).then((res) => {
        if (res.data.code === 0) {
          this.permissionData = res.data.data
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    // 选择权限后自动提交
    changePermission (row) {
      // row.permissionId  row.description   row.permission  row.available
      let type = 0
      if (row.available == '1') {
        // 激活
        type = 1
      } else {
        // 禁用
        type = 0
      }
      let requestParam = {
        roleId: this.roleObject.roleId,
        permissionId: row.permissionId,
        type: type
      }
      this.axios.get('authority/updateRolePermissionStatus', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {

        }
      }).catch((error) => {
        console.log(error)
      })
      // alert(JSON.stringify(requestParam));
    }

  }
}
