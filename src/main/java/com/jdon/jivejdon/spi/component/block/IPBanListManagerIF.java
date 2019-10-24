package com.jdon.jivejdon.spi.component.block;

import java.util.Collection;

public interface IPBanListManagerIF {

	public abstract boolean isBanned(String remoteIp);

	public abstract void addBannedIp(String ip);

	public abstract void deleteBannedIp(String ip);

	public abstract Collection getAllBanIpList();

	public abstract void clear();

}