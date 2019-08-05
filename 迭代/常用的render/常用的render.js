



{
	title: '性别',
          key: 'userSex',
          render (h, params) {
            let renderText = params.row.userSex === 'MAN' ? '男' : '女'
            // jsx
            return h('span', renderText)
          }
 },
==========================================================================================
 
 {
          title: '操作',
          key: 'action',
          width: 150,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'info',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.modify_show(params.index)
                  }
                }
              }, '修改'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.modify_remove(params.index)
                  }
                }
              }, '删除')
            ])
          }
        }
===================================================================================================================
 backGround: [],
 
 handleEnter (data, rootLength, nodeKey) {
      // 循环给到 背景颜色
      for (let i = 0; i < rootLength; i++) {
        this.backGround.push('#FFFFFF')
      }

      if (data) {
        // alert(this.backGround.length)
        this.backGround[nodeKey] = '#F5F5F5'
      } else {
        this.backGround[nodeKey] = '#FFFFFF'
      }
    },


  renderContent (h, { root, node, data }) {
      // let backGround=""
      /*    if (node.nodeKey%2 ===0){
        backGround="#F5F5F5"
      }else {
                backGround="#DCDCDC"
      } */
      return h('span', {
        style: {
          display: 'inline-block',
          width: '100%',
          backgroundColor: this.backGround[node.nodeKey]
        },
        on: {
          mouseenter: () => { this.handleEnter(true, root.length, node.nodeKey) },
          mouseleave: () => { this.handleEnter(false, root.length, node.nodeKey) }

        }
      }, [
        /* h('span', [
          h('Icon', {
            props: {
              type: 'ios-paper-outline'
            },
            style: {
              marginRight: '8px'
            }
          }),
          h('span', data.title)
        ]), */

        h('span', [
          h('Icon', {
            props: {
              type: 'ios-paper-outline'
            },
            style: {
              marginRight: '8px'
            }
          }),

          h('Poptip', {
            props: {
              trigger: 'hover',
              placement: 'right',
              [`word-wrap`]: true,
              width: '200',
              content: data.desc
            }
          },
          data.title
          )
        ]),
        h('span', {
          style: {
            display: 'inline-block',
            float: 'right',
            marginRight: '32px'
          }
        }, [
          h('Button', {
            props: Object.assign({}, this.buttonProps, {
              icon: 'ios-add'
            }),
            style: {
              marginRight: '8px'
            },
            on: {
              click: () => { this.append(data) }
            }
          }),
          h('Button', {
            props: Object.assign({}, this.buttonProps, {
              icon: 'md-create'
            }),
            style: {
              marginRight: '8px'
            },
            on: {
              click: () => { this.modify(data) }
            }
          }),
          h('Button', {
            props: Object.assign({}, this.buttonProps, {
              icon: 'ios-remove'
            }),
            on: {
              click: () => { this.remove(root, node, data) }
            }
          })
        ])
      ])
    },

===============================================================================
