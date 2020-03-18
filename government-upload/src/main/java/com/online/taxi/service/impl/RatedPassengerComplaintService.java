package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.RatedPassengerComplaintRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报乘客投诉信息
 * Created by wn on 2018/1/27.
 */
@Service("ratedPassengerComplaintService")
public class RatedPassengerComplaintService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        RatedPassengerComplaintRequest ratedPassengerComplaintRequest = new RatedPassengerComplaintRequest(IPCUrl.RATEDPASSENGERCOMPLAINT.getKey(), IPCUrl.RATEDPASSENGERCOMPLAINT.getValue());
        ratedPassengerComplaintRequest.setOrderId((String) message.get("OrderId"));
        ratedPassengerComplaintRequest.setComplaintTime((Long) message.get("ComplaintTime"));
        ratedPassengerComplaintRequest.setDetail((String) message.get("Detail"));

        return ratedPassengerComplaintRequest;
    }

}
