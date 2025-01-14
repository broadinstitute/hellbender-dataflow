package org.broadinstitute.hellbender.tools.dataflow.pipelines;

import org.broadinstitute.hellbender.cmdline.StandardArgumentDefinitions;
import org.broadinstitute.hellbender.engine.dataflow.DataflowCommandLineProgramTest;
import org.broadinstitute.hellbender.utils.test.ArgumentsBuilder;
import org.broadinstitute.hellbender.utils.text.XReadLines;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;


public final class CountBasesDataflowIntegrationTest extends DataflowCommandLineProgramTest {

    @Test(groups = "dataflow")
    public void testCountBasesFilteredIntegrationTestLocal() throws IOException {

        ArgumentsBuilder args = new ArgumentsBuilder();
        args.add("--"+StandardArgumentDefinitions.INPUT_LONG_NAME); args.add(new File(getTestDataDir(),"flag_stat.bam").getPath());
        args.add("--L"); args.add("chr7");
        args.add("--L"); args.add("chr8");
        args.add("--"+StandardArgumentDefinitions.OUTPUT_LONG_NAME);
        File placeHolder = createTempFile("countbasestest", ".txt");
        args.add(placeHolder.getPath());
        addDataflowRunnerArgs(args);

        runCommandLine(args.getArgsArray());
        File outputFile = findDataflowOutput(placeHolder);

        Assert.assertTrue(outputFile.exists());
        try(XReadLines readlines =  new XReadLines(outputFile)){
            Assert.assertEquals(Long.valueOf(readlines.next()), (Long) 505L);
        }

    }
}