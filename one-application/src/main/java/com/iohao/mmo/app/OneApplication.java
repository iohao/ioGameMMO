/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.mmo.app;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import com.iohao.mmo.broker.MyBrokerServer;
import com.iohao.mmo.external.MyExternalServer;
import com.iohao.mmo.login.LoginLogicServer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-21
 */
@Slf4j
public class OneApplication {
    public static void main(String[] args) {

        // 游戏逻辑服列表
        List<AbstractBrokerClientStartup> logicServers = listLogic();

        // 游戏对外服
        ExternalServer externalServer = new MyExternalServer()
                .createExternalServer(ExternalGlobalConfig.externalPort);

        BrokerServer brokerServer = new MyBrokerServer()
                .createBrokerServer();

        new NettyRunOne()
                // 游戏对外服
                .setExternalServer(externalServer)
                // Broker（游戏网关服）
                .setBrokerServer(brokerServer)
                // 游戏逻辑服列表
                .setLogicServerList(logicServers)
                // 启动游戏服务器
                .startup();
    }

    private static List<AbstractBrokerClientStartup> listLogic() {
        LoginLogicServer loginLogicServer = new LoginLogicServer();

        // 游戏逻辑服列表
        return List.of(
                // 登录
                loginLogicServer
        );
    }
}
