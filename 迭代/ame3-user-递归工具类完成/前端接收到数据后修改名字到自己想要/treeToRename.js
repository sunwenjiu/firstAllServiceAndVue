
/*
function f1(data) {
  let children=[]
  for(let i =0; i<data.length;i++){
    let obj={
      value:'',
      label:'',
      children:[]
    }
    obj.value= data[i].id
    obj.label=data[i].title
    children.push(obj)
    if(data[i].children.length>0){
      obj.children = f1(data[i].children)
    }
  }
  return children
}*/

export function treeToRename (data){
  let arr=[]
  for(let i =0; i<data.length;i++){
    let obj={
      value:'',
      label:'',
      children:[]
    }
    obj.value= data[i].id
    obj.label=data[i].title
    if(data[i].children.length>0){
      obj.children = treeToRename(data[i].children)
    }
    //obj.children=f1(data[i].children)
    arr.push(obj)
  }
  return arr

}
