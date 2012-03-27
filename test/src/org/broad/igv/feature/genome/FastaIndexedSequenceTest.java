/*
 * Copyright (c) 2007-2011 by The Broad Institute of MIT and Harvard.  All Rights Reserved.
 *
 * This software is licensed under the terms of the GNU Lesser General Public License (LGPL),
 * Version 2.1 which is available at http://www.opensource.org/licenses/lgpl-2.1.php.
 *
 * THE SOFTWARE IS PROVIDED "AS IS." THE BROAD AND MIT MAKE NO REPRESENTATIONS OR
 * WARRANTES OF ANY KIND CONCERNING THE SOFTWARE, EXPRESS OR IMPLIED, INCLUDING,
 * WITHOUT LIMITATION, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, NONINFRINGEMENT, OR THE ABSENCE OF LATENT OR OTHER DEFECTS, WHETHER
 * OR NOT DISCOVERABLE.  IN NO EVENT SHALL THE BROAD OR MIT, OR THEIR RESPECTIVE
 * TRUSTEES, DIRECTORS, OFFICERS, EMPLOYEES, AND AFFILIATES BE LIABLE FOR ANY DAMAGES
 * OF ANY KIND, INCLUDING, WITHOUT LIMITATION, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * ECONOMIC DAMAGES OR INJURY TO PROPERTY AND LOST PROFITS, REGARDLESS OF WHETHER
 * THE BROAD OR MIT SHALL BE ADVISED, SHALL HAVE OTHER REASON TO KNOW, OR IN FACT
 * SHALL KNOW OF THE POSSIBILITY OF THE FOREGOING.
 */

package org.broad.igv.feature.genome;

import org.broad.igv.util.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: jrobinso
 * Date: 8/7/11
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class FastaIndexedSequenceTest {


    static FastaIndexedSequence fastaSequence;

    @BeforeClass
    public static void setup() throws IOException {
        String path = "http://www.broadinstitute.org/igvdata/test/fasta/ci2_test.fa";
        fastaSequence = new FastaIndexedSequence(path);
    }

    /**
     * Compare a direct read of sequence from a file vs a read from and indexed fasta for the same interval.
     *
     * @throws Exception
     */
    @Test
    public void testReadSequence() throws Exception {

        String chr02qSeqPath = "http://www.broadinstitute.org/igvdata/test/fasta/";

        String chr = "chr02q";
        int start = 3531385;
        int end = 3531425;


        // TAATTTTTACGTCTTATTTAAACACATATAATGAATAGGT;
        Sequence igvSequence = new IGVSequence(chr02qSeqPath);
        byte[] expectedBytes = igvSequence.readSequence(chr, start, end);
        String expectedSequence = new String(expectedBytes);

         byte[] bytes = fastaSequence.readSequence(chr, start, end);
        String seq = new String(bytes);
        assertEquals(expectedSequence, seq);
    }

    @Test
    public void testReadEnd() throws Exception {

        String path = "http://www.broadinstitute.org/igvdata/test/fasta/ci2_test.fa";

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

    @Test
    public void testPaddedReference() throws Exception {

        String fasta = TestUtils.DATA_DIR + "/fasta/ecoli_out.padded.fasta";
        String expectedSequence = "atcaccattaccac******AAcggtgcgggctgacgcgtacaggaaacacagaaaaaag";
        String chr = "NC_000913_bb";
        int start = 240;
        int end = 300;
        FastaIndexedSequence sequence = new FastaIndexedSequence(fasta);

        byte[] bytes = sequence.readSequence(chr, start, end);

        assertEquals(expectedSequence, new String(bytes));

    }


    // TODO -- add some insertions, what are we testing?
    @Test
    public void testPaddedReference2() throws Exception {

        String fasta = TestUtils.DATA_DIR + "/fasta/ecoli_out.padded.fasta";
        String chr = "NC_000913_bb";
        int start = 0;
        int end = 5081;
        FastaIndexedSequence sequence = new FastaIndexedSequence(fasta);


        byte[] bytes = sequence.readSequence(chr, start, end);

        for (int i = 60; i < 100; i++) {

        }

        FastaIndexedSequence s = new FastaIndexedSequence(fasta);
        SequenceHelper sequenceHelper = new SequenceHelper(s);
        bytes = sequenceHelper.getSequence(chr, start, end, end);
        for (int i = 60; i < 100; i++) {
            // ?????? Not sure what to test here
        }

    }
}
