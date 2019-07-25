import $ from 'jquery'

export default {
  data () {
    return {
      resourceModal: false,
      viewResourceModal: false,
      childrenModal: false,
      permissionModal: false,
      delResourceModal: false,
      delChildrenModal: false,
      delPermissionModal: false,
      iconChangeModal: false,
      pageNum: 1, // 起始页
      pageSize: 72, // 每页显示的条目数
      totalPages: 10, // 手术列表总页数，默认1页
      iconData: [], // 图标数据
      modalTitle: '',
      // 新增或编辑菜单对象
      resourceObject: {
        resCode: '',
        resName: '',
        resDisplay: 'NO_LOCK',
        parent: {}
      },
      // 新增或编辑子菜单对象
      childrenObject: {
        resCode: '',
        resName: '',
        resUrl: '',
        resIcon: '',
        resDisplay: 'AFTER',
        parent: {}
      },
      // 新增或编辑权限对象
      permissionObject: {
        // perCode: '',
        // perName: '',
        // perDescription: '',
        // resource: {}
      },
      selections: [],
      selectNodes: [],
      ids: '',
      // 权限名称
      names: '',
      currentViewNode: {},
      // 菜单验证
      resourceValidate: {
        resCode: [{
          required: true,
          message: '请填写功能编码',
          trigger: 'blur'
        }],
        resName: [{
          required: true,
          message: '请填写功能名称',
          trigger: 'blur'
        }],
        resUrl: [{
          required: true,
          message: '请填写URL地址',
          trigger: 'blur'
        }]
      },
      // 子菜单验证
      childrenValidate: {
        resCode: [{
          required: true,
          message: '请填写功能编码',
          trigger: 'blur'
        }],
        resName: [{
          required: true,
          message: '请填写功能名称',
          trigger: 'blur'
        }],
        resUrl: [{
          required: true,
          message: '请填写URL地址',
          trigger: 'blur'
        }]
      },
      // 权限验证
      permissionValidate: {
        perCode: [{
          required: true,
          message: '请填写权限编码',
          trigger: 'blur'
        }],
        perName: [{
          required: true,
          message: '请填写权限名称',
          trigger: 'blur'
        }]
      },
      // 主菜单数据
      resourceData: [
        { 'resId': '1', 'resName': '权限管理', 'resCode': 'qwe', 'createTime': '23434', 'updateTime': 'sdsdsd' },
        { 'resId': '2', 'resName': '手术', 'resCode': 'qwe', 'createTime': '23434', 'updateTime': 'sdsdsd' },
        { 'resId': '3', 'resName': '病区', 'resCode': 'qwe', 'createTime': '23434', 'updateTime': 'sdsdsd' },
        { 'resId': '4', 'resName': '知识库', 'resCode': 'qwe', 'createTime': '23434', 'updateTime': 'sdsdsd' },
        { 'resId': '5', 'resName': '手术室表格', 'resCode': 'qwe', 'createTime': '23434', 'updateTime': 'sdsdsd' },
        { 'resId': '6', 'resName': '手术考勤', 'resCode': 'qwe', 'createTime': '23434', 'updateTime': 'sdsdsd' }
      ],
      // 子菜单树数据
      treeData: [],
      // 权限数据
      permissionData: [
        { perId: '1', perName: '呼叫新增', perCode: 'dsdadd', perDescription: '呼叫新增' },
        { perId: '1', perName: '呼叫编辑', perCode: 'dsdadd', perDescription: '呼叫编辑' },
        { perId: '1', perName: '呼叫查看', perCode: 'dsdadd', perDescription: '呼叫查看' },
        { perId: '1', perName: '呼叫删除', perCode: 'dsdadd', perDescription: '呼叫删除' },
        { perId: '1', perName: '呼叫删除', perCode: 'dsdadd', perDescription: '呼叫删除' },
        { perId: '1', perName: '呼叫删除', perCode: 'dsdadd', perDescription: '呼叫删除' }
      ],
      // 主菜单列表
      resourceColumns: [
        {
          title: '序号',
          type: 'selection',
          width: 60,
          align: 'center',
          key: 'dataSourcePnId'
        },
        {
          title: '功能名称',
          key: 'dataSourceName'
        },
        {
          title: '功能描述',
          key: 'dataSourceName'
        },
        {
          title: '激活状态',
          render: (h, params) => {
            return h('p', params.row.available === 0 ? '启用' : params.row.available === 1 ? '禁用' : '启用')
          }
        },
        {
          title: '创建时间',
          key: 'createTime',
          render: (h, params) => {
            return h('p', this.xdate.format(params.row.createTime, 'yyyy-MM-dd'))
          },
          sortable: true
        }, {
          title: '修改时间',
          key: 'updateTime',
          render: (h, params) => {
            return h('p', this.xdate.format(params.row.updateTime, 'yyyy-MM-dd HH:mm:ss'))
          },
          sortable: true
        }, {
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
                    this.resourceObject = $.extend(true, {}, params.row)
                    this.resourceObject.parent = {}
                    this.editResource()
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
                    this.viewResource(params.row)
                  }
                }
              }, '子级管理'))
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
                    this.resourceObject = $.extend(true, {}, params.row)
                    this.selections = []
                    this.selections.push(params.row)
                    this.deleteResource()
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
        },
        {
          title: '权限描述',
          key: 'description'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 200,
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
                  marginRight: '20px'
                },
                on: {
                  click: () => {
                    this.permissionObject = $.extend(true, {}, params.row)
                    this.editPermission()
                  }
                }
              }, '编辑'))
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
                    this.deletePermission(params.row)
                  }
                }
              }, '删除'))
            }

            return h('div', actions)
          }
        }
      ]
    }
  },
  mounted () {
    this.list()
  },
  methods: {
    cancel () {
      this.childrenModal = false
      this.resourceModal = false
      this.permissionModal = false
      this.$refs['resourceObject'].resetFields()
      this.$refs['childrenObject'].resetFields()
      this.$refs['permissionObject'].resetFields()
    },
    // 初始化数据
    list () {
      this.axios.get('authority/getDataSourcePnListByMain').then((res) => {
        if (res.data.code === 0) {
          this.resourceData = res.data.data
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    // 删除子菜单按钮
    delChildren () {
      if (this.selectNodes.length < 1) {
        this.$Notice.info({
          title: '提示',
          desc: '请至少选择一个节点！'
        })
        return false
      }
      this.delChildrenModal = true
      this.names = this.selectNodes[0].title
      // alert(JSON.stringify(this.selectNodes[0].dataSourcePnId));
      // alert(this.currentViewNode.dataSourcePnId);
    },
    // 删除子菜单提交
    delChildrenSubmit () {
      let requestParam = {
        psdId: this.selectNodes[0].dataSourcePnId
      }
      this.axios.get('authority/deleteDataSourcePnByMain', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          let requestParam = {
            parentId: this.currentViewNode.dataSourcePnId
          }
          this.axios.get('authority/getDataSourcePnListBySecondy', { params: requestParam }).then((res) => {
            if (res.data.code === 0) {
              this.treeData = res.data.data
            }
          }).catch((error) => {
            console.log(error)
          })
          this.selectNodes = []
          this.permissionData = []
        } else {
          this.$Notice.success({
            title: res.data.msg
          })
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    // 添加主菜单按钮
    addResource () {
      this.modalTitle = '添加功能'
      this.resourceObject = {
        dataSourcePnId: '',
        dataSourceName: '',
        available: 0
      }
      this.resourceModal = true
    },
    // 主菜单编辑按钮
    editResource () {
      this.modalTitle = '编辑功能'
      this.resourceModal = true
    },
    // 主菜单子级管理按钮
    viewResource (row) {
      this.modalTitle = '【 ' + row.dataSourceName + ' 】子功能管理'
      this.permissionData = []
      this.selectNodes = []
      this.currentViewNode = row
      let requestParam = {
        parentId: row.dataSourcePnId
      }
      this.axios.get('authority/getDataSourcePnListBySecondy', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.treeData = res.data.data
          console.log(this.treeData)
        }
      }).catch((error) => {
        console.log(error)
      })
      this.viewResourceModal = true
    },
    // 选择的子菜单节点
    selectNode (selectNodes) {
      if (selectNodes.length < 1) {
        this.selectNodes = []
        this.permissionData = []
        return false
      }
      this.selectNodes = selectNodes
      let requestParam = {
        dataSourcePnId: selectNodes[0].dataSourcePnId
      }
      this.axios.get('authority/getPermissionBySecondy', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.permissionData = res.data.data
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    // 添加子菜单按钮
    addChildren () {
      this.childrenModal = true
      this.childrenObject = {
        dataSourcePnId: '',
        dataSourceName: '',
        description: '',
        available: 0,
        isPartent: '1',
        partentId: this.currentViewNode.dataSourcePnId,
        parentName: this.currentViewNode.dataSourceName
      }
    },
    // 编辑子菜单按钮
    editChildren () {
      if (this.selectNodes.length < 1) {
        this.$Notice.info({
          title: '提示',
          desc: '请选择一个节点才能编辑！'
        })
        return false
      }
      this.childrenObject = {
        dataSourcePnId: this.selectNodes[0].dataSourcePnId,
        dataSourceName: this.selectNodes[0].title,
        description: this.selectNodes[0].title,
        available: 0,
        isPartent: '1',
        partentId: this.currentViewNode.dataSourcePnId,
        parentName: this.currentViewNode.dataSourceName
      }
      this.childrenModal = true
    },
    // 添加或编辑子菜单
    addEditChildren () {
      this.childrenModal = false
      let dataSourcePnDTO = {
        psdId: this.childrenObject.dataSourcePnId,
        psdName: this.childrenObject.dataSourceName,
        psdDes: this.childrenObject.dataSourceName,
        available: this.childrenObject.available,
        isPartent: this.childrenObject.isPartent,
        partentId: this.childrenObject.partentId
      }
      this.axios.post('authority/saveDataSourcePnByMain', dataSourcePnDTO).then((res) => {
        if (res.data.code === 0) {
          let requestParam = {
            parentId: this.childrenObject.partentId
          }
          this.axios.get('authority/getDataSourcePnListBySecondy', { params: requestParam }).then((res) => {
            if (res.data.code === 0) {
              this.treeData = res.data.data
            }
          }).catch((error) => {
            console.log(error)
          })
        } else {
          this.$Notice.success({
            title: res.data.msg
          })
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    // 批量及单个删除主菜单的按钮
    deleteResource () {
      this.ids = ''
      this.names = ''
      if (this.selections.length < 1) {
        this.$Notice.info({
          title: '提示',
          desc: '请至少选择一条数据删除！'
        })
        return false
      }
      for (let i = 0; i < this.selections.length; i++) {
        if (i === this.selections.length - 1) {
          this.ids += this.selections[i].resId
          this.names += this.selections[i].resName
        } else {
          this.ids += this.selections[i].resId + ','
          this.names += this.selections[i].resName + ','
        }
      }
      this.delResourceModal = true
    },
    // 批量及单个删除主菜单提交
    deleteResourceSubmit () {
      let requestParam = {
        psdId: this.resourceObject.dataSourcePnId
      }
      this.axios.get('authority/deleteDataSourcePnByMain', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.list()
        } else {
          this.$Notice.success({
            title: res.data.msg
          })
        }
      }).catch((error) => {
        console.log(error)
      })
      this.delResourceModal = false
    },
    // 删除权限按钮
    deletePermission (row) {
      this.delPermissionModal = true
      this.ids = row.permissionId
      this.names = row.description
    },
    // 删除权限提交
    delPermissionSubmit () {
      let requestParam = {
        perId: this.ids
      }
      this.axios.get('authority/deletePermissByDataSource', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          let requestParam = {
            dataSourcePnId: this.selectNodes[0].dataSourcePnId
          }
          this.axios.get('authority/getPermissionBySecondy', { params: requestParam }).then((res) => {
            if (res.data.code === 0) {
              this.permissionData = res.data.data
            }
          }).catch((error) => {
            console.log(error)
          })
        }
      }).catch((e) => {
        console.log(e)
      })
      this.delPermissionModal = false
    },
    // 主菜单选中项发生变化时触发
    selectionChange (selections) {
      this.selections = selections
    },
    // 编辑权限
    editPermission () {
      this.modalTitle = '编辑权限'
      this.permissionModal = true
    },
    // 主菜单点击排序时触发
    sortChange (column) {
      this.queryParams.orders = [{
        order: column.key,
        sort: column.order
      }]
      this.list()
    },
    // 添加或编辑主菜单提交
    addEditResource () {
      let dataSourcePnDTO = {
        psdId: this.resourceObject.dataSourcePnId,
        psdName: this.resourceObject.dataSourceName,
        psdDes: this.resourceObject.dataSourceName,
        available: this.resourceObject.available,
        isPartent: 0,
        partentId: 0
      }
      this.axios.post('authority/saveDataSourcePnByMain', dataSourcePnDTO).then((res) => {
        if (res.data.code === 0) {
          this.list()
        } else {
          this.$Notice.success({
            title: res.data.msg
          })
        }
      }).catch((error) => {
        console.log(error)
      })
      this.resourceModal = false
    },
    // 添加权限按钮
    addPermission () {
      if (this.selectNodes.length < 1) {
        this.$Notice.info({
          title: '提示',
          desc: '请至少选择一个节点！'
        })
        return false
      }
      this.permissionObject = {
        permissionId: '',
        permission: '',
        description: '',
        groupId: this.selectNodes[0].dataSourcePnId
      }
      this.modalTitle = '添加权限'
      this.permissionModal = true
      // alert(JSON.stringify(this.permissionObject));
    },
    // 添加或编辑权限提交
    addEditPermission () {
      let permissionDTO = {
        permissionIdStr: this.permissionObject.permissionId,
        permission: this.permissionObject.permission,
        available: 0,
        description: this.permissionObject.description,
        category: this.permissionObject.groupId
      }
      this.axios.post('authority/savePermissByDataSource', permissionDTO).then((res) => {
        if (res.data.code === 0) {
          let requestParam = {
            dataSourcePnId: this.selectNodes[0].dataSourcePnId
          }
          this.axios.get('authority/getPermissionBySecondy', { params: requestParam }).then((res) => {
            if (res.data.code === 0) {
              this.permissionData = res.data.data
            }
          }).catch((error) => {
            console.log(error)
          })
        }
      }).catch((error) => {
        console.log(error)
      })
      this.permissionModal = false
    },
    // 加载图标列表
    iconList (pageNum) {
      this.pageNum = pageNum
      let url = 'icon/findAfter?&pno=' + (pageNum - 1) + '&size=' + this.pageSize
      this.axios.get(url).then((res) => {
        if (res.data.code === 0) {
          this.iconData = res.data.data.content
          this.totalPages = res.data.data.totalPages * 10
        } else {
          this.iconData = []
        }
      })
    },
    // 图标选中后方法
    iconChangeOk (code) {
      this.resourceObject.resIcon = code
      this.childrenObject.resIcon = code
      this.iconChangeModal = false
    }
  }
}
