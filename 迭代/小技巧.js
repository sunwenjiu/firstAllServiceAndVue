java

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
