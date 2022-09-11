package com.chengzzz.zcloud.schedul;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;

/**
 * @author Yet
 * @date 2022/09/11 08:23
 **/

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ReflashSchedul {
//
//
////    @Autowired      //注入mapper
////    @SuppressWarnings("all")
////    CronMapper cronMapper;
//
//    /**
//     * 执行定时任务.
//     */
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//
//        taskRegistrar.addTriggerTask(
//                //1.添加任务内容(Runnable)
//                () -> System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime()),
//                //2.设置执行周期(Trigger)
//                triggerContext -> {
//                    //2.1 从数据库获取执行周期
//                    String cron = cronMapper.getCron();
//                    //2.2 合法性校验.
//                    if (StringUtils.isEmpty(cron)) {
//                        // Omitted Code ..
//                    }
//                    //2.3 返回执行周期(Date)
//                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
//                }
//        );
//    }

}
