package hello.advanced.trace;

import java.util.UUID;

public class TraceId {
    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level =level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public TraceId createNextId(){  //다음 레벨 id
        return new TraceId(id,level+1); //id는 같으나 레벨은 증가함
    }

    public TraceId createPreviousId(){  //이전 레벨 id
        return new TraceId(id,level-1); //id는 같으나 레벨 하나 감소함
    }

    public boolean isFirstLevel(){  //첫번째 여부 확인
        return level==0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
