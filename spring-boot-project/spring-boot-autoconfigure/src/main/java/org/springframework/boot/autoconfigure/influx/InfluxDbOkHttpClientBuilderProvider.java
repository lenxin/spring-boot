package org.springframework.boot.autoconfigure.influx;

import java.util.function.Supplier;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;

/**
 * Provide the {@link okhttp3.OkHttpClient.Builder OkHttpClient.Builder} to use to
 * customize the auto-configured {@link InfluxDB} instance.
 *

 * @since 2.1.0
 */
@FunctionalInterface
public interface InfluxDbOkHttpClientBuilderProvider extends Supplier<OkHttpClient.Builder> {

}
