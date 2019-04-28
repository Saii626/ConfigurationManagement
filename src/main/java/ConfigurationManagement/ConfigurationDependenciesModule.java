package ConfigurationManagement;

import dagger.Module;
import dagger.Provides;

import java.io.Reader;
import java.io.Writer;

@Module
public class ConfigurationDependenciesModule {

    private Reader reader;
    private Writer writer;

    public ConfigurationDependenciesModule(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Provides
    @ConfigurationScope
    public Reader getReader() {
        return this.reader;
    }

    @Provides
    @ConfigurationScope
    public Writer getWriter() {
        return writer;
    }
}
