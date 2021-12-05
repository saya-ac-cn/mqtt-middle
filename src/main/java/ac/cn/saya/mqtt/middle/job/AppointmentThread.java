package ac.cn.saya.mqtt.middle.job;

import ac.cn.saya.mqtt.middle.entity.IotAppointmentEntity;

/**
 * 定时发送指令
 * @Title: SendCommandThreadTask
 * @ProjectName spring-boot-pro
 * @Author saya
 * @Date: 2021/12/4 17:02
 * @Description: TODO
 */

public class AppointmentThread implements Runnable{

    private IotAppointmentEntity appointment;

    public AppointmentThread(IotAppointmentEntity appointment) {
        this.appointment = appointment;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.err.println("下发指令："+appointment.getCommand());
    }
}
