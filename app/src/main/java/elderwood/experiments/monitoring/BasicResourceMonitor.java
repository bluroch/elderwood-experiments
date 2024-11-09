package elderwood.experiments.monitoring;

import lombok.extern.slf4j.Slf4j;
import net.minestom.server.MinecraftServer;
import net.minestom.server.monitoring.BenchmarkManager;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.timer.TaskSchedule;

@Slf4j
public class BasicResourceMonitor {

  private static final SchedulerManager scheduler = MinecraftServer.getSchedulerManager();
  private static final BenchmarkManager usageStats = MinecraftServer.getBenchmarkManager();

  public static void addMonitor() {
    scheduler.submitTask(() -> {
      log.info("Memory usage: {} MiB", String.format("%.2f", (float) usageStats.getUsedMemory() / (1024 * 1024)));
      return TaskSchedule.seconds(5);
    });
    log.info("Resource monitor added");
  }
}
