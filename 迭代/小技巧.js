java
=======================================================
  public  List<Dict> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Dict> dicts = dictRepository.findAll(sort);
        //将其他 排在最后
        List<Dict> sortDicts = new ArrayList<>();
        List<Dict> endDIcts= new ArrayList<>();
        for (Dict d:dicts  ) {
          int index =  d.getName().indexOf("其他");
          if(index == -1){
              sortDicts.add(d);
          }else {
              endDIcts.add(d);
          }
        }
        for (Dict d:endDIcts
             ) {
            sortDicts.add(d);
        }

        return sortDicts;
    }
	===========================================
vue
树节点 隐藏子节点的添加
  style: {
              marginRight: '8px',
              display: (data.parentId === '0')?"inline-block":"none"
            },
			
			======================================================================

java
    /**
     * 通过子节点类型名字，找出一级分类
     *
     * @param typeName
     * @return
     */
    private String getEventParentByEventTypeName(String typeName) {

        List<EventType> eventTypes = eventTypeRepository.findAll();
        String typeId = null;
        for (EventType e : eventTypes
                ) {
            if (e.getName().equals(typeName)) {
                typeId = e.getEventTypeId();
            }
        }
        if (typeId == null) {
            throw new ParameterInvalidException("传入的事件类型名字有误");
        }
        String id = getEventParentName(eventTypes, typeId);

        return id;
    }

    /**
     * 通过子节点类型名字，找出一级分类的递归
     *
     * @param list
     * @param typeID
     * @return
     */
    private String getEventParentName(List<EventType> list, String typeID) {
        String eventId = null;

        String parent = null;
        for (EventType e : list
                ) {
            if (e.getEventTypeId().equals(typeID)) {
                parent = e.getParentId();
                if (!parent.equals("0")) {
                    eventId= getEventParentName(list, parent);
                } else {
                    eventId = e.getEventTypeId();

                    break;
                }

            }
        }

        return eventId;
    }
	
	========================================================================