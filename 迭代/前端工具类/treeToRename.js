
/*
*   import {treeToRename }from './treeToRename'
*
*   */
export function treeToRename (data) {
  let arr = []
  for (let i = 0; i < data.length; i++) {
    let obj = {
      value: '',
      label: '',
      children: []
    }
    obj.value = data[i].id
    obj.label = data[i].title
    if (data[i].children.length > 0) {
      obj.children = treeToRename(data[i].children)
    }
    // obj.children=f1(data[i].children)
    arr.push(obj)
  }
  return arr
}

//value和label都用title

export function treeTitleToRename (data) {
  let arr = []
  for (let i = 0; i < data.length; i++) {
    let obj = {
      value: '',
      label: '',
      children: []
    }
    obj.value = data[i].title
    obj.label = data[i].title
    if (data[i].children.length > 0) {
      obj.children = treeTitleToRename(data[i].children)
    }
    // obj.children=f1(data[i].children)
    arr.push(obj)
  }
  return arr
}

//获取事件类型的数组-通过一个子类型，获取全部的父类
export function getParent(data, id) {
 let arr= getParents(data, id)
  let arrSort=[]
  for(let i=arr.length-1 ;i>=0;i--){
   arrSort.push(arr[i])
  }
 // console.log("内")
 // console.log(arr)
  //console.log(arrSort)
  return arrSort
}
export function getParents(data, id) {
 // console.log("内")
 // console.log(data)
  //console.log(id)
  for (var i in data) {
    if (data[i].value == id) {
      return [data[i].value];
    }
    if (data[i].children) {
      var ro = getParents(data[i].children, id);
      if (ro !== undefined) {
        return ro.concat(data[i].value);
      }
    }
  }

}


// 修改名字给下拉选择
export function allToRename (data) {
  let arr = []
  for (let i = 0; i < data.length; i++) {
    let obj = {
      value: '',
      label: ''

    }
    obj.value = data[i].name
    obj.label = data[i].name
    arr.push(obj)
  }
  return arr
}

// 字典库修改名字给下拉选择
export function dictToRename (data) {
  let arr = []
  for (let i = 0; i < data.length; i++) {
    let obj = {
      value: '',
      label: ''

    }
    obj.value = data[i].dictId
    obj.label = data[i].name
    arr.push(obj)
  }
  return arr
}
// 部门修改名字给下拉选择
export function deptToRename (data) {
  let arr = []
  for (let i = 0; i < data.length; i++) {
    let obj = {
      value: '',
      label: ''

    }
    obj.value = data[i].deptId
    obj.label = data[i].name
    arr.push(obj)
  }
  return arr
}
// 病区修改名字给下拉选择
export function wardToRename (data) {
  let arr = []
  for (let i = 0; i < data.length; i++) {
    let obj = {
      value: '',
      label: ''

    }
    obj.value = data[i].wardId
    obj.label = data[i].name
    arr.push(obj)
  }
  return arr
}

