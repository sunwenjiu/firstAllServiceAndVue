package com.ame.ser.service.impl;import com.ame.ser.config.WebSocket;import com.ame.ser.dto.MessageDTO;import com.ame.ser.enums.EventStatusEnum;import com.ame.ser.enums.MessageStatusEnum;import com.ame.ser.enums.MessageTitleAndContentEnum;import com.ame.ser.enums.MessageTypeEnum;import com.ame.ser.model.Event;import com.ame.ser.model.Message;import com.ame.ser.model.User;import com.ame.ser.repository.MessageRepository;import com.ame.ser.repository.UserRepository;import com.ame.ser.service.MessageService;import com.ame.ser.service.ex.ParameterInvalidException;import com.ame.ser.utils.PrimaryKeyUtil;import org.apache.commons.lang3.StringUtils;import org.springframework.beans.BeanUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.data.domain.Sort;import org.springframework.data.jpa.domain.Specification;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import javax.persistence.criteria.CriteriaBuilder;import javax.persistence.criteria.CriteriaQuery;import javax.persistence.criteria.Predicate;import javax.persistence.criteria.Root;import javax.xml.crypto.Data;import java.text.SimpleDateFormat;import java.util.*;/** * 消息业务层实现类 * @athor SWJ * @Date 2019/8/21 */@Servicepublic class MessageServiceImpl implements MessageService {    @Autowired    private MessageRepository messageRepository;    @Autowired    private WebSocket webSocket;    @Autowired    private UserRepository userRepository;    /**     * 创建消息,(非公告消息，是事件有变动时创建)     * @param userIds  接收人集合     * @param initiatorId 发送人id     * @param title 标题     * @param content 消息内容     */    @Transactional    @Override    public void createMessage(List<String> userIds, String initiatorId, String title, String content) {        if(StringUtils.isEmpty(initiatorId) ||  StringUtils.isEmpty(title) || StringUtils.isEmpty(content) ){            throw new ParameterInvalidException("传入的参数有误，不能为空");        }        if (title.length()>32){            throw new ParameterInvalidException("标题过长，限制32");        }        //通过发起人的ID找出发起人的username        Optional<User> user = userRepository.findById(initiatorId);        String initiatorName = null;        if (user.isPresent()) {             initiatorName = user.get().getUserName();        }        Date now = new Date();        if(userIds == null){            //userIds 为空，表示发全通知            String msgIdForNull = PrimaryKeyUtil.getPrimaryId();            Message message = new Message(msgIdForNull, title, initiatorId, null, content, MessageStatusEnum.NO_DEFAULT,MessageTypeEnum.PROCLAMATION, now, now,initiatorName);            try {                messageRepository.saveAndFlush(message);                // TODO 发送websocket给全局人                webSocket.sendAllMessage(MessageTitleAndContentEnum.HANDLER_3.getMessage());            }catch (Exception e){                throw new ParameterInvalidException("保存数据失败");            }        }else {            //userIds不为空，表示独立发给某些人            for (String receivedId:userIds                    ) {                String msgId = PrimaryKeyUtil.getPrimaryId();                Message message = new Message(msgId, title, initiatorId, receivedId, content, MessageStatusEnum.UNREAD,MessageTypeEnum.NOTICE, now, now,initiatorName);                try {                    messageRepository.saveAndFlush(message);                    // TODO  发送websocket 给接收人                    webSocket.sendOneMessage(receivedId, MessageTitleAndContentEnum.HANDLER_3.getMessage());                }catch (Exception e){                    throw new ParameterInvalidException("保存数据失败");                }            }        }    }    /**     * 查询未读消息的条数     * @param userId 需要查询的UserID     * @return     */    @Override    public long messageUnreadCount(String userId) {        Specification<Message> specification = new Specification<Message>() {            private static final long serialVersionUID = 1L;            @Override            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {                Predicate condition1 = criteriaBuilder.equal(root.<String>get("receivedId"), userId);                Predicate condition2 =criteriaBuilder.equal(root.<Integer>get("messageStatus"),MessageStatusEnum.UNREAD);                criteriaQuery.where(condition1,condition2);                return null;            }        };       long count = messageRepository.count(specification);        return count;    }    /**     * 把一条消息标记为已读     * @param msgId 消息id     */    @Transactional    @Override    public Integer messageHasRead(String msgId) {        Integer count=0;        try{            count= messageRepository.updateMessageStatusByMsgId(MessageStatusEnum.READED, msgId,new Date());        }catch (Exception e){            throw new ParameterInvalidException("数据保存失败");        }        return count;    }    /**     * 通过消息ID返回该消息的内容     * @param msgId 消息id     * @return     */    @Override    public String getMessageContentById(String msgId) {      Optional<Message>  message= messageRepository.findById(msgId);      String content =null;      if (message.isPresent()){          content = message.get().getMessageContent();      }else {          throw new ParameterInvalidException("查询数据失败");      }        return content;    }    /**     * 初始化(查询)该用户的所有消息     * @param userId 该用户     * @return     */    @Override    public Map<String, List> getMessageList(String userId) {      List<Message> listMessage=  messageRepository.findAllByReceivedId(userId);        List<MessageDTO> listMessageDTO =new ArrayList<>();      List<MessageDTO> listUnread =new ArrayList<>(); //未读列表      List<MessageDTO> listReaded =new ArrayList<>(); //已读列表      List<MessageDTO> listNodefault =new ArrayList<>(); //通知        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");        //查出全局消息 开始        Specification<Message> specification = new Specification<Message>() {            private static final long serialVersionUID = 1L;            @Override            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {                Predicate condition1 = criteriaBuilder.equal(root.<String>get("messageType"), MessageTypeEnum.PROCLAMATION);                criteriaQuery.where(condition1);                return null;            }        };        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");       List<Message> listMessageProclamation = messageRepository.findAll(specification,sort); //全局消息       // List<MessageDTO> listMessageProclamationDTO =new ArrayList<>();        for (Message m:listMessageProclamation             ) {            MessageDTO messageDTO = new MessageDTO();            String createTime =sdf.format(m.getCreateTime());            BeanUtils.copyProperties(m, messageDTO);            messageDTO.setCreateTime(createTime);            //消除内容<减少传输量>            messageDTO.setMessageContent(null);           // listMessageProclamationDTO.add(messageDTO);            listNodefault.add(messageDTO);        }        //全局处理结束        //处理创建时间的数据  并且复制对象 已读和未读        for (Message m:listMessage             ) {            String createTime =sdf.format(m.getCreateTime());            MessageDTO messageDTO = new MessageDTO();            BeanUtils.copyProperties(m, messageDTO);            messageDTO.setCreateTime(createTime);            //消除内容<减少传输量>            messageDTO.setMessageContent(null);            listMessageDTO.add(messageDTO);        }        for (MessageDTO m :listMessageDTO             ) {            if (MessageStatusEnum.UNREAD.equals(m.getMessageStatus())){                //未读                listUnread.add(m);            }            if (MessageStatusEnum.READED.equals(m.getMessageStatus())){                listReaded.add(m);            }        }        Map<String,List> listMap = new HashMap<>();        listMap.put("unread",listUnread );        listMap.put("readed", listReaded);        listMap.put("nodefault", listNodefault);        return listMap;    }    /**     * 分页查询全局通知消息     * @param pageNo     * @param pageSize     * @return     */    @Override    public Page<Message> findAllPage(int pageNo, int pageSize) {        Specification<Message> specification = new Specification<Message>() {            private static final long serialVersionUID = 1L;            @Override            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {                Predicate condition1 = criteriaBuilder.equal(root.<String>get("messageType"), MessageTypeEnum.PROCLAMATION);                criteriaQuery.where(condition1);                return null;            }        };        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);        Page<Message>  messages=  messageRepository.findAll(specification, pageable);        return messages;    }    /**     * 通过id批量删除数据     * @param ids     */    @Transactional    @Override    public void deleteByMsgIdIn(List<String> ids) {        try {            messageRepository.deleteByMsgIdIn(ids);        }catch (Exception e){            throw new ParameterInvalidException("传入的参数有误");        }    }    /**     * 获取不同消息类型，不同条数的消息     * @param messageType     * @param limit     * @return     */    @Override    public List<Message> getListByType(int messageType,String receiveId, int limit) {        List<Message> result = null;        if(receiveId != null && receiveId.length() > 0){            result = messageRepository.getListByTypeInEvent(messageType,receiveId,limit);        }else {            result = messageRepository.getListByTypeInNotic(messageType,limit);        }        return result;    }}