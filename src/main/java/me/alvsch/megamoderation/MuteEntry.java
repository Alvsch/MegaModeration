package me.alvsch.megamoderation;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("Mute")
public class MuteEntry implements ConfigurationSerializable {

    private final UUID target;
    private final String reason;
    private final Date expires;
    private final UUID source;

    public MuteEntry(UUID target, String reason, Date expires, UUID source) {
        this.target = target;
        this.reason = reason;
        this.expires = expires;
        this.source = source;
    }


    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("target", target.toString());
        result.put("reason", reason);
        if(expires != null) {
            result.put("expires", expires.getTime());
        }
        if(source != null) {
            result.put("source", source.toString());
        }
        return result;
    }

    public static MuteEntry deserialize(Map<String, Object> args) {

        UUID target = UUID.fromString((String) args.get("target"));
        String reason = (String) args.get("reason");

        Date expires = null;
        if(args.get("expires") != null) {
            expires = new Date(Long.parseLong(String.valueOf(args.get("expires"))));
        }
        UUID source = null;
        if(args.get("expires") != null) {
            source = UUID.fromString((String) args.get("source"));
        }

        return new MuteEntry(target, reason, expires, source);
    }


    public UUID getTarget() {
        return target;
    }

    public String getReason() {
        return reason;
    }

    public Date getExpiration() {
        return expires;
    }

    public UUID getSource() {
        return source;
    }
}
