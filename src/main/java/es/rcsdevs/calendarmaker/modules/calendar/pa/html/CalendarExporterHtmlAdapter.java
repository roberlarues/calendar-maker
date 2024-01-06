package es.rcsdevs.calendarmaker.modules.calendar.pa.html;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import es.rcsdevs.calendarmaker.modules.calendar.domain.constants.Constants;
import es.rcsdevs.calendarmaker.modules.calendar.domain.ports.CalendarExporter;

@Service(Constants.EXPORTER_HTML)
public class CalendarExporterHtmlAdapter implements CalendarExporter<String> {

    @Override
    public String exportCalendar(Map<String, Object> calendarDataMap) {
        String content = "";
        try {
            Template template = loadTemplate((String) calendarDataMap.get("calendarDir"));
            content = template.apply(calendarDataMap);
        } catch (IOException e) {
            throw new RuntimeException("error", e);
        }

        return content;
    }

    private Template loadTemplate(String directory) throws IOException {
        TemplateLoader loader = new FileTemplateLoader("");
        loader.setPrefix(directory + "/");
        loader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("indexModThree", new Helper<Integer>() {

            @Override
            public Object apply(Integer index, Options options) throws IOException {
                if (index % 3 == 0) {
                    return options.fn();
                } else {
                    return options.inverse();
                }
            }
            
        });
        Template template = handlebars.compile("template");

        return template;
    }
}
