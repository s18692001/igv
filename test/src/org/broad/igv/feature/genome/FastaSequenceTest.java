package org.broad.igv.feature.genome;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Jim Robinson
 * @date 3/26/12
 */
public class FastaSequenceTest {

    static FastaSequence fastaSequence;

    @BeforeClass
    public static void setup() throws IOException {
        String path = "http://www.broadinstitute.org/igvdata/test/fasta/ci2_test.fa";
        fastaSequence = new FastaSequence(path);
    }

    @Test
    public void testReadSequence() throws Exception {


        String chr = "chr02q";
        int start = 3531385;
        int end = 3531425;

        String expectedSequence = "TAATTTTTACGTCTTATTTAAACACATATAATGAATAGGT";

        byte[] seq = fastaSequence.readSequence(chr, start, end);
        String seqString = new String(seq);

        assertEquals(expectedSequence, seqString);
    }

    @Test
    public void testReadEnd() throws Exception {

        String chr = "chr02q";
        int chrLen = 8059593;
        int start = chrLen - 10;
        int end = chrLen + 10;
        byte[] bytes = fastaSequence.readSequence(chr, start, end);
        assertEquals(10, bytes.length);

        byte[] expectedSequence = "TTTTTCCCAG".getBytes();

        for (int i = 0; i < 10; i++) {
            assertEquals(expectedSequence[i], bytes[i]);
        }
    }


}


