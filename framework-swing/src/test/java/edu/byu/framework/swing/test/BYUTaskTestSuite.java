package edu.byu.framework.swing.test;

import edu.byu.framework.swing.exceptions.ExceptionListener;
import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import edu.byu.framework.swing.util.BYUTask;
import edu.byu.framework.swing.util.Retry;
import edu.byu.framework.swing.util.TaskEvent;
import edu.byu.framework.swing.util.TaskListener;
import edu.byu.framework.swing.util.TaskOptions;
import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author jmooreoa
 */
public class BYUTaskTestSuite {

    private final static Logger LOG = Logger.getLogger(BYUTaskTestSuite.class);
    private final static ExceptionListener exceptionListener = new ExceptionListener() {

        public void handleException(Throwable exception) {
            LOG.error("Handled Exception ", exception);
        }
    };

    @BeforeClass
    public static void suppressDialogs() {
        System.setProperty(ExceptionInternalHandler.SUPRESS_DIALOG, "true");
    }
    //Tests to make sure that all methods get executed
    @Test
    public void methodExecution() {
        final CountDownLatch latch = new CountDownLatch(3);
        BYUTask task = new BYUTask() {

            @Override
            protected void setup() {
                latch.countDown();
            }

            @Override
            protected void doInBackground() {
                latch.countDown();
            }

            @Override
            protected void tearDown() {
                latch.countDown();
            }
        };
        task.execute();
        awaitLatch(latch);
    }

    @Test
    public void methodOrder() {
        final CountDownLatch latch = new CountDownLatch(3);
        BYUTask task = new BYUTask() {

            private int count = 1;

            @Override
            protected void setup() {
                assertEquals("setup run out of order", 1, count);
                count++;
                latch.countDown();
            }

            @Override
            protected void doInBackground() {
                assertEquals("doInBackground run out of order", 2, count);
                count++;
                latch.countDown();
            }

            @Override
            protected void tearDown() {
                assertEquals("tearDown run out of order", 3, count);
                latch.countDown();
            }
        };
        task.execute();
        awaitLatch(latch);
    }

    @Test
    public void testBYUTaskTaskListenerNotification() {
        final CountDownLatch latch = new CountDownLatch(5);
        BYUTask task = new BYUTask() {

            @Override
            protected void setup() {
                latch.countDown();
            }

            @Override
            protected void doInBackground() {
                latch.countDown();
            }

            @Override
            protected void tearDown() {
                latch.countDown();
            }
        };
        TaskListener listener = new TaskListener() {

            public void taskStarted(TaskEvent task) {
                latch.countDown();
            }

            public void taskFinished(TaskEvent task) {
                latch.countDown();
            }
        };

        BYUTask.addTaskListener(listener);

        task.execute();
        awaitLatch(latch);
    }

    @Test
    public void tearDownAlwaysRunsAfterSetupError() {
        final CountDownLatch latch = new CountDownLatch(1);
        ErrorTask task = new ErrorTask(/*latch,*/true, false, false);
        TaskListener listener = new TaskListener() {

            public void taskStarted(TaskEvent event) {
            }

            public void taskFinished(TaskEvent event) {
                latch.countDown();
            }
        };
        ErrorTask.addTaskListener(listener);

        task.execute();
        awaitLatch(latch);
        assertTrue("Did not run setup", task.didSetup);
        assertFalse("Ran doInBackground", task.didDoInBackground);
        assertTrue("Did not run tearDown", task.didTearDown);
    }

    @Test
    public void tearDownAlwaysRunsAfterBackgroundError() {
        final CountDownLatch latch = new CountDownLatch(1);
        ErrorTask task = new ErrorTask(/*latch,*/false, true, false);
        TaskListener listener = new TaskListener() {

            public void taskStarted(TaskEvent event) {
            }

            public void taskFinished(TaskEvent event) {
                latch.countDown();
            }
        };
        ErrorTask.addTaskListener(listener);

        task.execute();
        awaitLatch(latch);
        assertTrue("Did not run setup", task.didSetup);
        assertTrue("Did not run doInBackground", task.didDoInBackground);
        assertTrue("Did not run tearDown", task.didTearDown);
    }

    @Test
    public void taskHadError() {
        final CountDownLatch latch1 = new CountDownLatch(1);
        ErrorTask task = new ErrorTask(/*latch,*/true, false, false);
        TaskListener listener = new TaskListener() {

            public void taskStarted(TaskEvent event) {
            }

            public void taskFinished(TaskEvent event) {
                latch1.countDown();
            }
        };
        ErrorTask.addTaskListener(listener);

        task.execute();
        awaitLatch(latch1);
        assertTrue("Did not register error", task.hadError());

        final CountDownLatch latch2 = new CountDownLatch(1);
        task = new ErrorTask(/*latch,*/true, false, false);
        listener = new TaskListener() {

            public void taskStarted(TaskEvent event) {
            }

            public void taskFinished(TaskEvent event) {
                latch2.countDown();
            }
        };
        ErrorTask.addTaskListener(listener);

        task.execute();
        awaitLatch(latch2);
        assertTrue("Did not register error", task.hadError());
    }

    @Test
    public void runOnProperThreads() {
        final CountDownLatch latch = new CountDownLatch(3);
        EDTTestTask task = new EDTTestTask(latch);
        task.execute();
        awaitLatch(latch);
        assertTrue("setup not run on EDT", task.setup);
        assertFalse("background run on EDT", task.background);
        assertTrue("tearDown not run on EDT", task.tearDown);
    }

    @Test
    public void runOnProperThreadsWithErrors() {
        final CountDownLatch latch = new CountDownLatch(2);
        EDTErrorTask setup = new EDTErrorTask(latch, true, false, false);
        EDTErrorTask background = new EDTErrorTask(latch, false, true, false);

        EDTErrorTask.addExceptionListener(exceptionListener);
        setup.execute();
        background.execute();
        
        awaitLatch(latch);


        assertTrue("setup not run on EDT with error in setup", setup.setup);
        assertTrue("tearDown not run on EDT with error in setup", setup.tearDown);

        assertTrue("setup not run on EDT with error in background", background.setup);
        assertFalse("doInBackground run on EDT with error in background", background.background);
        assertTrue("tearDown not run on EDT with error in background", background.tearDown);
    }

    @Test
    public void retry() {
        CountDownLatch latch = new CountDownLatch(2);
        AnnotatedErrorTask success = new AnnotatedErrorTask(latch, false);
        AnnotatedErrorTask fail = new AnnotatedErrorTask(latch, true);
        success.run();
        fail.run();

        awaitLatch(latch);

        assertTrue(success.isFinished());
        assertFalse(fail.isFinished());

        assertTrue(success.tries == 2);
        assertTrue(fail.tries == 1);
    }

    private void awaitLatch(CountDownLatch latch) {
        try {
            boolean ran = latch.await(2, TimeUnit.SECONDS);
            assertTrue("Not all methods were run", ran);
        } catch (InterruptedException ex) {
            throw new IllegalStateException("Waiting thread interrupted", ex);
        }
    }

    private class EDTTestTask extends BYUTask {

        boolean setup, background, tearDown;
        CountDownLatch latch;

        public EDTTestTask(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        protected void setup() {
            setup = EventQueue.isDispatchThread();
            latch.countDown();
        }

        @Override
        protected void tearDown() {
            tearDown = EventQueue.isDispatchThread();
            latch.countDown();
        }

        @Override
        protected void doInBackground() {
            background = EventQueue.isDispatchThread();
            latch.countDown();
        }
    }

    private class EDTErrorTask extends ErrorTask {

        volatile boolean setup, background, tearDown;
        CountDownLatch latch;

        public EDTErrorTask(CountDownLatch latch, boolean inSetup, boolean inBackground, boolean inTearDown) {
            super(inSetup, inBackground, inTearDown);
            this.latch = latch;
        }

        @Override
        protected void setup() {
            setup = EventQueue.isDispatchThread();
            super.setup();
        }

        @Override
        protected void tearDown() {
            tearDown = EventQueue.isDispatchThread();
            super.tearDown();
            latch.countDown();
            
        }

        @Override
        protected void doInBackground() {
            background = EventQueue.isDispatchThread();
            super.doInBackground();
        }
    }

    private class AnnotatedErrorTask extends BYUTask {
        private final Exception retry = new IOException("Retry it!");
        private final Exception die = new FileNotFoundException("Die!");

        private final boolean killIt;
        private boolean threwUp;

        private boolean finished;

        private final CountDownLatch latch;

        private int tries;

        public AnnotatedErrorTask(CountDownLatch latch, boolean killIt) {
            this.latch = latch;
            this.killIt = killIt;
        }

        public boolean isFinished() {
            return finished;
        }

        public int getTries() {
            return tries;
        }

        @Override
        @TaskOptions(newThread=true)
        @Retry(retry=IOException.class, die=FileNotFoundException.class, tries=2)
        protected void doInBackground() throws Exception {
            tries++;
            if (threwUp) {
                finished = true;
                return;
            }
            threwUp = true;
            if (killIt) {
                throw die;
            }
            throw retry;
        }

        @Override
        protected void tearDown() {
            latch.countDown();
        }
    }

    private class ErrorTask extends BYUTask {

        private boolean didSetup = false,  didDoInBackground = false,  didTearDown = false;
        private final boolean inSetup,  inBackground,  inTearDown;

        public ErrorTask(boolean inSetup, boolean inBackground, boolean inTearDown) {
            this.inSetup = inSetup;
            this.inBackground = inBackground;
            this.inTearDown = inTearDown;
        }

        public boolean getDidDoInBackground() {
            return didDoInBackground;
        }

        public boolean getDidSetup() {
            return didSetup;
        }

        public boolean getDidTearDown() {
            return didTearDown;
        }

        @Override
        protected void setup() {
            didSetup = true;
            if (inSetup) {
                throw new IllegalStateException("Test error: setup");
            }
        }

        @Override
        @TaskOptions(newThread=true)
        protected void doInBackground() {
            didDoInBackground = true;
            if (inBackground) {
                throw new IllegalStateException("Test error: doInBackground");
            }
        }

        @Override
        protected void tearDown() {
            didTearDown = true;
            if (inTearDown) {
                throw new IllegalStateException("Test error: tearDown");
            }
        }
    }

}
