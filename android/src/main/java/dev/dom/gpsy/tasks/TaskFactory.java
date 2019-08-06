package dev.dom.gpsy.tasks;

import dev.dom.gpsy.OnCompletionListener;
import dev.dom.gpsy.data.SnrOptions;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class TaskFactory {
    public static Task<SnrOptions> createSnrUpdatesTask(
            PluginRegistry.Registrar registrar,
            MethodChannel.Result result,
            Object arguments,
            OnCompletionListener completionListener) {

        SnrOptions options = SnrOptions.parseArguments(arguments);
        TaskContext<SnrOptions> taskContext = TaskContext.buildForMethodResult(
                registrar,
                result,
                options,
                completionListener);

        return new SnrUpdatesUsingLocationManagerTask(taskContext);
    }
}